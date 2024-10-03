package com.pnu.sursim.domain.surveyanswer.service;

import com.pnu.sursim.domain.survey.entity.*;
import com.pnu.sursim.domain.survey.repository.QuestionOptionRepository;
import com.pnu.sursim.domain.survey.repository.QuestionRepository;
import com.pnu.sursim.domain.survey.repository.SurveyRepository;
import com.pnu.sursim.domain.surveyanswer.dto.*;
import com.pnu.sursim.domain.surveyanswer.entity.SurveyAnswer;
import com.pnu.sursim.domain.surveyanswer.repository.*;
import com.pnu.sursim.domain.surveyanswer.util.AnswerFactory;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyAnswerService {

    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final QuestionRepository questionRepository;
    private final DescriptiveAnswerRepository descriptiveAnswerRepository;
    private final OptionsAnswerRepository optionsAnswerRepository;
    private final ScaleAnswerRepository scaleAnswerRepository;
    private final TextAnswerRepository textAnswerRepository;
    private final QuestionOptionRepository questionOptionRepository;


    public SurveyResult getSurveyResult(String email, long surveyId) {
        User user = getUserOrThrow(email);
        Survey targetSurvey = getSurveyOrThrow(surveyId);

        if ( !targetSurvey.isShared() && !validateCreatorOfSurvey(user, targetSurvey)) {
            throw new CustomException(ErrorCode.SURVEY_RESULTS_ARE_PROTECTED);
        }

        List<QuestionResult> questionResults = completeQuestionResultList(targetSurvey);

        return new SurveyResult(targetSurvey, surveyAnswerRepository.countBySurvey(targetSurvey), questionResults);

    }

    private List<QuestionResult> completeQuestionResultList(Survey survey) {

        return questionRepository.findAllBySurveyIdOrderByIndexAsc(survey.getId())
                .stream()
                .map(question -> {
                    QuestionType questionType = question.getQuestionType();
                    //문항의 타입이 단일/체크 경우 ChoiceQuestionResponse 반환
                    if ((questionType == QuestionType.CHECK_CHOICE) || (questionType == QuestionType.MULTIPLE_CHOICE)) {
                        //각 옵션
                        List<OptionResult> optionResultList = questionOptionRepository.findAllByQuestionIdOrderByIndexAsc(question.getId())
                                .stream()
                                .map(questionOption -> new OptionResult(questionOption, optionsAnswerRepository.countByQuestionOption(questionOption)))
                                .toList();

                        return new OptionQuestionResult(question, optionsAnswerRepository.countByQuestion(question), optionResultList);
                    }

                    //문항 타입이 의미판별인 경우 변경 -> SemanticQuestionResponse
                    if (questionType == QuestionType.SEMANTIC_RATINGS || questionType == QuestionType.LIKERT_SCORES) {
                        List<OptionResult> optionResultList = new ArrayList<>();

                        optionResultList.add(new OptionResult(Scale.ONE, scaleAnswerRepository.countByQuestionAndScale(question,Scale.ONE)));
                        optionResultList.add(new OptionResult(Scale.TWO, scaleAnswerRepository.countByQuestionAndScale(question,Scale.TWO)));
                        optionResultList.add(new OptionResult(Scale.THREE, scaleAnswerRepository.countByQuestionAndScale(question,Scale.THREE)));
                        optionResultList.add(new OptionResult(Scale.FOUR, scaleAnswerRepository.countByQuestionAndScale(question,Scale.FOUR)));
                        optionResultList.add(new OptionResult(Scale.FIVE, scaleAnswerRepository.countByQuestionAndScale(question,Scale.FIVE)));

                        return new OptionQuestionResult(question, scaleAnswerRepository.countByQuestion(question), optionResultList);
                    }

                    if (questionType == QuestionType.DESCRIPTIVE) {
                        return new QuestionResult(question, descriptiveAnswerRepository.countByQuestion(question));
                    }
                    //일반 문항 -> QuestionResponse
                    return new QuestionResult(question, textAnswerRepository.countByQuestion(question));
                })
                .toList();
    }

    private boolean validateCreatorOfSurvey(User user, Survey targetSurvey) {
        return targetSurvey.getCreator().equals(user);
    }


    //유저의 서베이 응답 정보를 저장함
    @Transactional
    public void saveSurveyAnswer(String email, long surveyId, SurveyAnswerRequest surveyAnswerRequest) {
        validateSurveyAnswerRequest(surveyAnswerRequest);

        User answeringUser = getUserOrThrow(email);
        Survey targetSurvey = getSurveyOrThrow(surveyId);

        validateAnswerEligibility(answeringUser, targetSurvey);
        validateFirstResponse(answeringUser, targetSurvey);

        //응답 저장하기
        SurveyAnswer savedSurveyAnswer = surveyAnswerRepository.save(SurveyAnswer.builder()
                .answeringUser(answeringUser)
                .writtenDate(LocalDate.now())
                .survey(targetSurvey)
                .consentStatus(surveyAnswerRequest.getConsentStatus())
                .build());

        //서베이의 문항가져오기
        List<Question> questionList = questionRepository.findAllBySurveyIdOrderByIndexAsc(targetSurvey.getId());

        //응답자의 응답 리스트 생성 + 유효성 판단
        AnswerRequestList validAnswerRequestList = new AnswerRequestList(surveyAnswerRequest.getQuestionAnswerList())
                .validateAnswers(questionList);

        // 각 문항에 대해 응답을 매핑하고 저장 처리
        saveAllAnswers(savedSurveyAnswer, questionList, validAnswerRequestList);

        // 유저 포인트 누적
        answeringUser.accumulatePoint(targetSurvey.getPoints());
    }

    private void saveAllAnswers(SurveyAnswer savedSurveyAnswer, List<Question> questionList, AnswerRequestList validAnswerRequestList) {
        questionList.forEach(question -> {
            if (validAnswerRequestList.isOptionalAndNotExist(question)) {
                return;
            }

            AnswerRequest answerRequest = validAnswerRequestList.getAnswerById(question);
            saveAnswerByQuestionType(savedSurveyAnswer, question, answerRequest);
        });
    }

    private void saveAnswerByQuestionType(SurveyAnswer savedSurveyAnswer, Question question, AnswerRequest answerRequest) {
        QuestionType answerQuestionType = answerRequest.getQuestionType();

        //체크 응답인 경우
        if (answerQuestionType == QuestionType.CHECK_CHOICE) {
            List<Integer> checkList = answerRequest.getCheckNumberList();

            checkList.forEach(
                    index -> {
                        QuestionOption questionOption = getQuestionOptionByIndexOrThrow(question.getId(), index);
                        optionsAnswerRepository.save(AnswerFactory.makeOptionsAnswer(savedSurveyAnswer, question, questionOption, index));
                    }
            );
        }

        //객관식인 경우
        if (answerQuestionType == QuestionType.MULTIPLE_CHOICE) {
            QuestionOption questionOption = getQuestionOptionByIndexOrThrow(question.getId(), answerRequest.getOptionNumber());
            optionsAnswerRepository.save(AnswerFactory.makeOptionsAnswer(savedSurveyAnswer, question, questionOption, answerRequest.getOptionNumber()));
        }

        //의미분별척도나, 리커트척도인 경우
        if ((answerQuestionType == QuestionType.LIKERT_SCORES) || (answerQuestionType == QuestionType.SEMANTIC_RATINGS)) {
            scaleAnswerRepository.save(AnswerFactory.makeScaleAnswer(savedSurveyAnswer, question, answerRequest.getScale()));
        }

        //텍스트 응답인 경우 (전화번호,숫자응답,주관식)
        if ((answerQuestionType == QuestionType.PHONE_NUMBER) || (answerQuestionType == QuestionType.NUMERIC_RESPONSE) || (answerQuestionType == QuestionType.SUBJECTIVE)) {
            textAnswerRepository.save(AnswerFactory.makeTextAnswer(savedSurveyAnswer, question, answerRequest.getText()));
        }

        //서술형인 경우
        if (answerQuestionType == QuestionType.DESCRIPTIVE) {
            descriptiveAnswerRepository.save(AnswerFactory.makeDescriptiveAnswer(savedSurveyAnswer, question, answerRequest.getText()));
        }


    }

    private QuestionOption getQuestionOptionByIndexOrThrow(Long questionId, int index) {
        return questionOptionRepository.findByQuestionIdAndIndex(questionId, index)
                .orElseThrow(() -> new CustomException(ErrorCode.INCORRECT_OPTIONS_ANSWER));
    }

    //유저의 유효성을 검사하고 예외를 발생시키는 부분
    private User getUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));
    }

    private Survey getSurveyOrThrow(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_DOES_NOT_EXIST));
    }

    private boolean validateAnswerEligibility(User user, Survey survey) {
        //성별검사
        if (!survey.matchesAgeCriteria(user.getUserAge())) {
            throw new CustomException(ErrorCode.SURVEY_GENDER_AND_USER_GENDER_DO_NOT_MATCH);
        }

        if (!survey.matchesGenderCriteria(user.getGender())) {
            throw new CustomException(ErrorCode.SURVEY_GENDER_AND_USER_GENDER_DO_NOT_MATCH);

        }
        return true;
    }

    private boolean validateFirstResponse(User user, Survey survey) {
        if (surveyAnswerRepository.existsBySurveyIdAndAnsweringUserId(survey.getId(), user.getId())) {
            throw new CustomException(ErrorCode.SURVEY_ANSWER_EXISTS);
        }
        return true;
    }

    private boolean validateSurveyAnswerRequest(SurveyAnswerRequest surveyAnswerRequest) {
        if (!surveyAnswerRequest.getConsentStatus().isAgreed()) {
            throw new CustomException(ErrorCode.SURVEY_CONSENT_REQUIRED);
        }
        return true;
    }


}


package com.pnu.sursim.domain.surveyanswer.service;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.QuestionOption;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import com.pnu.sursim.domain.survey.entity.Survey;
import com.pnu.sursim.domain.survey.repository.QuestionOptionRepository;
import com.pnu.sursim.domain.survey.repository.QuestionRepository;
import com.pnu.sursim.domain.survey.repository.SurveyRepository;
import com.pnu.sursim.domain.surveyanswer.dto.AnswerRequest;
import com.pnu.sursim.domain.surveyanswer.dto.AnswerRequestList;
import com.pnu.sursim.domain.surveyanswer.dto.SurveyAnswerRequest;
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


    //유저의 서베이 응답 정보를 저장함
    @Transactional
    public void saveSurveyAnswer(String email, long surveyId, SurveyAnswerRequest surveyAnswerRequest) {
        //동의가 필요함
        if (!surveyAnswerRequest.getConsentStatus().isAgreed()) {
            throw new CustomException(ErrorCode.SURVEY_CONSENT_REQUIRED);
        }

        // 유저와 설문 조사 엔티티 가져오기
        User answeringUser = findUserOrThrow(email);
        Survey targetSurvey = findSurveyOrThrow(surveyId);
        LocalDate writtenDate = LocalDate.now();


        //응답 저장하기
        SurveyAnswer savedSurveyAnswer = surveyAnswerRepository.save(SurveyAnswer.builder()
                .answeringUser(answeringUser)
                .writtenDate(writtenDate)
                .survey(targetSurvey)
                .consentStatus(surveyAnswerRequest.getConsentStatus())
                .build());

        //서베이의 문항가져오기
        List<Question> questionList = questionRepository.findAllBySurveyIdOrderByIndexAsc(targetSurvey.getId());

        //응답자의 응답 리스트 생성 + 유효성 판단
        AnswerRequestList validAnswerRequestList = new AnswerRequestList(surveyAnswerRequest.getQuestionAnswerList()).validateAnswers(questionList);

        // 각 문항에 대해 응답을 매핑하고 저장 처리
        questionList.forEach(question -> {
            // 응답을 가져와서 문항에 매핑 (유효성 검사는 이미 validateAnswers에서 처리됨)
            if (validAnswerRequestList.isOptionalAndNotExist(question)) {
                return;
            }

            AnswerRequest answerRequest = validAnswerRequestList.getAnswerById(question);

            //존재하지 않지만 선택인 경우는 넘어가도록 처리

            //체크 응답인 경우
            if (answerRequest.getQuestionType() == QuestionType.CHECK_CHOICE) {
                List<Integer> checkList = answerRequest.getCheckNumberList();

                checkList.forEach(
                        index -> {
                            QuestionOption questionOption = getQuestionOptionByIndexOrThrow(question.getId(), index);
                            optionsAnswerRepository.save(AnswerFactory.makeOptionsAnswer(savedSurveyAnswer, question, questionOption, index));
                        }

                );
            }

            //객관식인 경우
            if (answerRequest.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
                QuestionOption questionOption = getQuestionOptionByIndexOrThrow(question.getId(), answerRequest.getOptionNumber());
                optionsAnswerRepository.save(AnswerFactory.makeOptionsAnswer(savedSurveyAnswer, question, questionOption, answerRequest.getOptionNumber()));
            }


            //의미분별척도나, 리커트척도인 경우
            if ((answerRequest.getQuestionType() == QuestionType.LIKERT_SCORES) || (answerRequest.getQuestionType() == QuestionType.SEMANTIC_RATINGS)) {
                scaleAnswerRepository.save(AnswerFactory.makeScaleAnswer(savedSurveyAnswer, question, answerRequest.getScale()));
            }

            //텍스트 응답인 경우 (전화번호,숫자응답,주관식)
            if ((answerRequest.getQuestionType() == QuestionType.PHONE_NUMBER) || (answerRequest.getQuestionType() == QuestionType.NUMERIC_RESPONSE) || (answerRequest.getQuestionType() == QuestionType.SUBJECTIVE)) {
                textAnswerRepository.save(AnswerFactory.makeTextAnswer(savedSurveyAnswer, question, answerRequest.getText()));
            }

            //서술형인 경우
            if (answerRequest.getQuestionType() == QuestionType.DESCRIPTIVE) {
                descriptiveAnswerRepository.save(AnswerFactory.makeDescriptiveAnswer(savedSurveyAnswer, question, answerRequest.getText()));
            }
        });

        answeringUser.accumulatePoint(targetSurvey.getPoints());
    }

    private QuestionOption getQuestionOptionByIndexOrThrow(Long questionId,int index) {
        return questionOptionRepository.findByQuestionIdAndIndex(questionId, index)
                .orElseThrow(() -> new CustomException(ErrorCode.INCORRECT_OPTIONS_ANSWER));
    }

    //유저의 유효성을 검사하고 예외를 발생시키는 부분
    private User findUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));
    }

    public Survey findSurveyOrThrow(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_DOES_NOT_EXIST));
    }
}

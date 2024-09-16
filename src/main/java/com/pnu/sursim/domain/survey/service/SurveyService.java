package com.pnu.sursim.domain.survey.service;

import com.pnu.sursim.domain.survey.dto.QuestionResponse;
import com.pnu.sursim.domain.survey.dto.SurveyRequest;
import com.pnu.sursim.domain.survey.dto.SurveyResponse;
import com.pnu.sursim.domain.survey.entity.*;
import com.pnu.sursim.domain.survey.repository.QuestionOptionRepository;
import com.pnu.sursim.domain.survey.repository.QuestionRepository;
import com.pnu.sursim.domain.survey.repository.SemanticOptionRepository;
import com.pnu.sursim.domain.survey.repository.SurveyRepository;
import com.pnu.sursim.domain.survey.util.SurveyFactory;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SurveyService {

    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final SemanticOptionRepository semanticOptionRepository;

    @Transactional
    public void createSurvey(String email, SurveyRequest surveyRequest) {
        User creator = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));

        Survey savedSurvey = surveyRepository.save(SurveyFactory.makeSurvey(surveyRequest, creator));

        //문항을 만드는 부분
        List<Question> questions = surveyRequest.questionList().stream()
                .map(questionRequest -> {
                    // Question 객체 생성 & 데이터베이스에 Question 저장
                    Question savedQuestion = questionRepository.save(SurveyFactory.makeQuestion(questionRequest, savedSurvey));

                    //응답옵션이 필요한 경우에 응답만들기
                    if ((savedQuestion.getQuestionType() == QuestionType.CHECK_CHOICE) || (savedQuestion.getQuestionType() == QuestionType.MULTIPLE_CHOICE)) {
                        // 체크박스거나 객관식인 경우
                        List<QuestionOption> questionOptions = questionRequest.questionOption().stream()
                                .map(questionOptionRequest -> questionOptionRepository.save(SurveyFactory.makeOption(questionOptionRequest, savedQuestion)))
                                .collect(Collectors.toList());
                    }

                    if (savedQuestion.getQuestionType() == QuestionType.SEMANTIC_RATINGS) {
                        // 의미 분별 척도인 경우
                        SemanticOption semanticOption = semanticOptionRepository.save(SurveyFactory.makeSemantic(questionRequest.semanticOption(), savedQuestion));

                    }

                    // 수정된 Question 반환
                    return savedQuestion;
                })
                .collect(Collectors.toList());

    }

    public Page<SurveyResponse> getSurveysForAll(String email, Pageable pageable) {
        Page<Survey> surveys = surveyRepository.findAll(pageable);
        Page<SurveyResponse> surveyResponsePage = new PageImpl<>(surveys.getContent().stream()
                .map(survey -> {
                    //서베이의 문항을 적절하게 변환하는 로직
                    List<QuestionResponse> questionResponses = questionRepository.findAllBySurveyIdOrderByIndexAsc(survey.getId())
                            .stream()
                            .map(question -> {
                                //문항의 타입이 단일/체크 경우 변경
                                if ((question.getQuestionType() == QuestionType.CHECK_CHOICE) || (question.getQuestionType() == QuestionType.MULTIPLE_CHOICE)) {
                                    List<QuestionOption> questionOptions = questionOptionRepository.findAllByQuestionIdOrderByIndexAsc(question.getId());
                                    if (questionOptions.isEmpty()) {
                                        throw new CustomException(ErrorCode.INCORRECT_CHOICE_QUESTION);
                                    }
                                    return SurveyFactory.makeChoiceQuestionResponse(question, questionOptions);
                                }

                                //문항 타입이 의미판별인 경우 변경
                                if (question.getQuestionType() == QuestionType.SEMANTIC_RATINGS) {
                                    SemanticOption semanticOption = semanticOptionRepository.findByQuestionId(question.getId())
                                            .orElseThrow(() -> new CustomException(ErrorCode.INCORRECT_SEMANTIC_QUESTIONS));
                                    return SurveyFactory.makeSemanticQuestionResponse(question, semanticOption);
                                }
                                return SurveyFactory.makeQuestionResponse(question);
                            })
                            .collect(Collectors.toList());

                    return SurveyFactory.makeSurveyResponse(survey, questionResponses);
                }).collect(Collectors.toList()), pageable, surveys.getTotalElements());

        return surveyResponsePage;
    }

    public Page<SurveyResponse> getSurveysForUser(String email, Pageable pageable) {
        return null;
    }


}

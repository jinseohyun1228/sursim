package com.pnu.sursim.domain.survey.service;

import com.pnu.sursim.domain.survey.dto.QuestionRequest;
import com.pnu.sursim.domain.survey.dto.SurveyRequest;
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

                    if (savedQuestion.getQuestionType() == QuestionType.LIKERT_SCORES) {
                        // 의미 분별 척도인 경우
                        SemanticOption semanticOption = semanticOptionRepository.save(SurveyFactory.makeSemantic(questionRequest.semanticOption(),savedQuestion));

                    }

                    // 수정된 Question 반환
                    return savedQuestion;
                })
                .collect(Collectors.toList());

    }

    private void createQuestion(QuestionType questionType, QuestionRequest question) {
    }


}

package com.pnu.sursim.domain.surveyanswer.service;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.surveyanswer.dto.QuestionAnswerRequest;
import com.pnu.sursim.domain.survey.dto.QuestionList;
import com.pnu.sursim.domain.surveyanswer.dto.SurveyAnswerRequest;
import com.pnu.sursim.domain.survey.entity.Survey;
import com.pnu.sursim.domain.surveyanswer.entity.CheckAnswer;
import com.pnu.sursim.domain.surveyanswer.entity.SurveyAnswer;
import com.pnu.sursim.domain.survey.repository.*;
import com.pnu.sursim.domain.surveyanswer.repository.SurveyAnswerRepository;
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


    //유저의 서베이 응답 정보를 저장함
    @Transactional
    public void saveSurveyAnswer(String email,  long surveyId, SurveyAnswerRequest surveyAnswerRequest) {
        //동의가 필요함
        if (!surveyAnswerRequest.getConsentStatus().isAgreed()) {
            throw new CustomException(ErrorCode.SURVEY_CONSENT_REQUIRED);
        }

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
        QuestionList questionList = new QuestionList(questionRepository.findAllBySurveyIdOrderByIndexAsc(targetSurvey.getId()));

        //응답자의 문항 요청
        List<QuestionAnswerRequest> answerRequestList = surveyAnswerRequest.getQuestionAnswerList();

        //문항만들기
        answerRequestList.forEach(
                answerResponse -> {
                    Question targetQuestion = questionList.getQuestionById(answerResponse.getId());

                    //체크 박스

                    //객관식인 경우

                    //일반 string 응답들

                    //서술형인 경우

                }
        );

    }

    //유저의 유효성을 검사하고 예외를 발생시키는 부분
    private User findUserOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_ERROR));
    }

    public Survey findSurveyOrThrow(Long surveyId){
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_DOES_NOT_EXIST));
    }


    private CheckAnswer completeAnswer(Survey survey, Question question, QuestionAnswerRequest questionAnswerRequest) {

    }

}

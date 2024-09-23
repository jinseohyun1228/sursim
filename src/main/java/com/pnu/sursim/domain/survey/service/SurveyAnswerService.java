package com.pnu.sursim.domain.survey.service;

import com.pnu.sursim.domain.survey.dto.SurveyAnswerRequest;
import com.pnu.sursim.domain.survey.entity.Survey;
import com.pnu.sursim.domain.survey.repository.*;
import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.domain.user.repository.UserRepository;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyAnswerService {

    private final UserRepository userRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;


    //유저의 서베이 응답 정보를 저장함
    @Transactional
    public void saveSurveyAnswer(long surveyId, AuthUser authUser, SurveyAnswerRequest surveyAnswerRequest) {
        //동의가 필요함
        if (!surveyAnswerRequest.getConsentStatus().isAgreed()) {
            throw new CustomException(ErrorCode.SURVEY_CONSENT_REQUIRED);
        }

        Survey targetSurvey = findSurveyOrThrow(surveyId);

        //


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
}

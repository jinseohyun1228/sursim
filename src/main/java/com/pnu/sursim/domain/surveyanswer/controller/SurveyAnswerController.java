package com.pnu.sursim.domain.surveyanswer.controller;

import com.pnu.sursim.domain.survey.service.SurveyService;
import com.pnu.sursim.domain.surveyanswer.dto.SurveyAnswerRequest;
import com.pnu.sursim.domain.surveyanswer.service.SurveyAnswerService;
import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.global.auth.resolver.SessionUser;
import com.pnu.sursim.global.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SurveyAnswerController {

    private final SurveyService surveyService;
    private final SurveyAnswerService surveyAnswerService;


    //서베이 응답하기 POST /surveys/{surveyId}/responses
    @PostMapping("/surveys/{id}/responses")
    public CustomResponse submitSurveyAnswer(@PathVariable("id") long surveyId, @SessionUser AuthUser authUser, @RequestBody SurveyAnswerRequest surveyAnswerRequest) {
        surveyAnswerService.saveSurveyAnswer(authUser.getEmail(), surveyId, surveyAnswerRequest);
        return CustomResponse.success("The user's response has been successfully saved.");
    }


}

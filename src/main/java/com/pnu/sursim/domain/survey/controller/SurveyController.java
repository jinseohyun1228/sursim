package com.pnu.sursim.domain.survey.controller;

import com.pnu.sursim.domain.survey.dto.RewardRequest;
import com.pnu.sursim.domain.survey.dto.SurveyRequest;
import com.pnu.sursim.domain.survey.dto.SurveyResponse;
import com.pnu.sursim.domain.survey.service.SurveyService;
import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.global.auth.resolver.SessionUser;
import com.pnu.sursim.global.response.CustomPage;
import com.pnu.sursim.global.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/surveys")
    public CustomResponse createSurvey(@SessionUser AuthUser authUser, @RequestBody SurveyRequest surveyRequest) {
        Long id = surveyService.createSurvey(authUser.getEmail(),surveyRequest);
        return CustomResponse.success(id);
    }

    @GetMapping("/surveys/all")
    public CustomResponse getSurveysForAll(@SessionUser AuthUser authUser,Pageable pageable) {
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveysForAll(pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    @GetMapping("/surveys")
    public CustomResponse getSurveysForUser(@SessionUser AuthUser authUser, Pageable pageable) {
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveysForUser(authUser.getEmail(),pageable);
        return CustomResponse.success(surveyResponsePage);
    }

    @GetMapping("/surveys/{id}/reward")
    public CustomResponse addReward(@SessionUser AuthUser authUser, @PathVariable("id")long surveyId, @RequestBody RewardRequest rewardRequest, @RequestParam("reward-img") MultipartFile rewardFile) {
        surveyService.addReward(authUser.getEmail(),surveyId,rewardRequest,rewardFile);
        return CustomResponse.success("Reward registration has been successfully completed.");
    }

}

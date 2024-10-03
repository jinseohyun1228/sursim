package com.pnu.sursim.domain.survey.controller;

import com.pnu.sursim.domain.survey.dto.RewardRequest;
import com.pnu.sursim.domain.survey.dto.SpecSurveyResponse;
import com.pnu.sursim.domain.survey.dto.SurveyRequest;
import com.pnu.sursim.domain.survey.dto.SurveyResponse;
import com.pnu.sursim.domain.survey.service.SurveyService;
import com.pnu.sursim.domain.surveyanswer.dto.SurveyAnswerRequest;
import com.pnu.sursim.domain.surveyanswer.service.SurveyAnswerService;
import com.pnu.sursim.domain.user.dto.AuthUser;
import com.pnu.sursim.global.auth.resolver.SessionUser;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
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
    private final SurveyAnswerService surveyAnswerService;

    //서베이에 리워드 추가 요청
    @PostMapping("/surveys")
    public CustomResponse createSurvey(@SessionUser AuthUser authUser,
                                    @RequestPart("survey") SurveyRequest surveyRequest,
                                    @RequestPart(value = "reward", required = false) RewardRequest rewardRequest,
                                    @RequestPart(value = "reward_file", required = false) MultipartFile rewardFile){
        if ((rewardFile == null && rewardRequest != null) || (rewardFile != null && rewardRequest == null)) {
            throw new CustomException(ErrorCode.REWARD_REQUEST_INVALID);
        }
        //둘다 있는 경우..에 넣어야하나?
        surveyService.createSurvey(authUser.getEmail(), surveyRequest,rewardRequest, rewardFile);
        return CustomResponse.success("Reward registration has been successfully completed.");
    }

    //모든 서베이 페이지 요청
    @GetMapping("/surveys/all")
    public CustomResponse getSurveyPageForAll(@SessionUser AuthUser authUser, Pageable pageable) {
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveyPageForAll(pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    //유저에 맞는 서베이 페이지 요청
    @GetMapping("/surveys")
    public CustomResponse getSurveyPageForUser(@SessionUser AuthUser authUser, Pageable pageable) {
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveyPageForUser(authUser.getEmail(), pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    //유저에 맞는 리워드 포함 서베이 페이지 요청
    @GetMapping("/surveys/reward")
    public CustomResponse getSurveyPageForReward(@SessionUser AuthUser authUser, Pageable pageable) {
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveyPageForReward(authUser.getEmail(), pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    //유저에 맞는 리워드 포함 서베이 세개만 제공
    @GetMapping("/surveys/reward/top3")
    public CustomResponse getSurveysForReward(@SessionUser AuthUser authUser) {
        List<SurveyResponse> surveyResponseList = surveyService.getSurveysForRewardTop3(authUser.getEmail());
        return CustomResponse.success(surveyResponseList);
    }


    //id기준 서베이 조회
    @GetMapping("/surveys/{id}")
    public CustomResponse addReward(@PathVariable("id") long surveyId) {
        SpecSurveyResponse SpecSurveyResponse = surveyService.getSpecSurveysById(surveyId);
        return CustomResponse.success(SpecSurveyResponse);
    }

    //서베이 응답하기 POST /surveys/{surveyId}/responses
    @PostMapping("/surveys/{id}/responses")
    public CustomResponse submitSurveyAnswer(@PathVariable("id") long surveyId, @SessionUser AuthUser authUser, @RequestBody SurveyAnswerRequest surveyAnswerRequest) {
        surveyAnswerService.saveSurveyAnswer(authUser.getEmail(), surveyId, surveyAnswerRequest);
        return CustomResponse.success("The user's response has been successfully saved.");
    }


}

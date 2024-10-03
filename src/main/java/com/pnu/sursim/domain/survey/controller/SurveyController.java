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
                                       @RequestPart(value = "reward_file", required = false) MultipartFile rewardFile) {
        if ((rewardFile == null && rewardRequest != null) || (rewardFile != null && rewardRequest == null)) {
            throw new CustomException(ErrorCode.REWARD_REQUEST_INVALID);
        }

        surveyService.createSurvey(authUser.getEmail(), surveyRequest, rewardRequest, rewardFile);
        return CustomResponse.success("Reward registration has been successfully completed.");
    }

    //서베이 페이지 요청
    @GetMapping("/surveys")
    public CustomResponse getSurveyPage(@SessionUser(required = false) AuthUser authUser, Pageable pageable) {
        if (authUser == null) {
            return CustomResponse.success(new CustomPage(surveyService.getSurveyPageForAll(pageable)));
        }
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveyPageForUser(authUser.getEmail(), pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    //리워드 포함 서베이 페이지 요청
    @GetMapping("/surveys/has-reward")
    public CustomResponse getSurveyPageHasReward(@SessionUser(required = false) AuthUser authUser, Pageable pageable) {
        if (authUser == null) {
            return CustomResponse.success(new CustomPage(surveyService.getSurveyPageHasRewardForAll(pageable)));
        }
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveyPageHasRewardForUser(authUser.getEmail(), pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }


    //설문이 공개된 서베이 요청
    @GetMapping("/surveys/open-results")
    public CustomResponse getSurveyPageShared(@SessionUser(required = false) AuthUser authUser, Pageable pageable) {
        if (authUser == null) {
            throw new CustomException(ErrorCode.LOGIN_ERROR);
        }
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveyPageShared(pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    //내가 만든 서베이 요청
    @GetMapping("/surveys/mine")
    public CustomResponse getSurveyPageMy(@SessionUser AuthUser authUser, Pageable pageable) {
        Page<SurveyResponse> surveyResponsePage = surveyService.getSurveyPageMy(authUser.getEmail(), pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }


    //id기준 서베이 조회
    @GetMapping("/surveys/{id}")
    public CustomResponse addReward(@PathVariable("id") long surveyId) {
        SpecSurveyResponse SpecSurveyResponse = surveyService.getSpecSurveysById(surveyId);
        return CustomResponse.success(SpecSurveyResponse);
    }


}

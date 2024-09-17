package com.pnu.sursim.domain.survey.controller;

import com.pnu.sursim.domain.survey.dto.RewardRequest;
import com.pnu.sursim.domain.survey.dto.SurveyRequest;
import com.pnu.sursim.domain.survey.dto.SurveyResponseRecode;
import com.pnu.sursim.domain.survey.entity.RewardType;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/surveys")
    public CustomResponse createSurvey(@SessionUser AuthUser authUser, @RequestBody SurveyRequest surveyRequest) {
        Map<String, Long> responseMap = new HashMap<>();
        Long id = surveyService.createSurvey(authUser.getEmail(),surveyRequest);
        responseMap.put("survey_id", id);
        return CustomResponse.success(responseMap);
    }

    //모든 서베이 페이지 요청
    @GetMapping("/surveys/all")
    public CustomResponse getSurveyPageForAll(@SessionUser AuthUser authUser,Pageable pageable) {
        Page<SurveyResponseRecode> surveyResponsePage = surveyService.getSurveyPageForAll(pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    //유저에 맞는 서베이 페이지 요청
    @GetMapping("/surveys")
    public CustomResponse getSurveyPageForUser(@SessionUser AuthUser authUser, Pageable pageable) {
        Page<SurveyResponseRecode> surveyResponsePage = surveyService.getSurveyPageForUser(authUser.getEmail(),pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    //유저에 맞는 리워드 포함 서베이 페이지 요청
    @GetMapping("/surveys/reward")
    public CustomResponse getSurveyPageForReward(@SessionUser AuthUser authUser, Pageable pageable) {
        Page<SurveyResponseRecode> surveyResponsePage = surveyService.getSurveyPageForReward(authUser.getEmail(),pageable);
        return CustomResponse.success(new CustomPage(surveyResponsePage));
    }

    //유저에 맞는 리워드 포함 서베이 세개만 제공
    @GetMapping("/surveys/reward/top3")
    public CustomResponse getSurveysForReward(@SessionUser AuthUser authUser) {
        List<SurveyResponseRecode> surveyResponseRecodeList = surveyService.getSurveysForRewardTop3(authUser.getEmail());
        return CustomResponse.success(surveyResponseRecodeList);
    }

    //서베이에 리워드 추가 요청
    @PostMapping("/surveys/{id}/reward")
    public CustomResponse addReward(@SessionUser AuthUser authUser,
                                    @PathVariable("id") long surveyId,
                                    @RequestParam("title") String title,
                                    @RequestParam("reward_type") RewardType rewardType,
                                    @RequestParam("count") int count,
                                    @RequestParam("reward_file") MultipartFile rewardFile){ // 파일은 MultipartFile로 받음
        RewardRequest rewardRequest = new RewardRequest(title, rewardType, count);
        surveyService.addReward(authUser.getEmail(),surveyId,rewardRequest,rewardFile);
        return CustomResponse.success("Reward registration has been successfully completed.");
    }

    //서베이에 리워드 추가 요청
    @GetMapping("/surveys/{id}")
    public CustomResponse addReward(@SessionUser AuthUser authUser, @PathVariable("id") long surveyId){
        SurveyReson
        return CustomResponse.success("Reward registration has been successfully completed.");
    }



}

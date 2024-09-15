package com.pnu.sursim.domain.survey.util;

import com.pnu.sursim.domain.survey.dto.SurveyRequest;

public class SurveyRequiredTimeCalculator {

    public static int calculateRequiredTime(SurveyRequest surveyRequest) {
        int timeRequired = surveyRequest.questionList()
                .stream() // 문항 리스트를 스트림으로 변환
                .mapToInt(questionRequest -> questionRequest.questionType().getTimeTaken()) // 각 문항의 소요 시간을 정수로 매핑
                .sum();
        return timeRequired;
    }
}

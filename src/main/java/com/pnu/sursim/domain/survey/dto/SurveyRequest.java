package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.PublicAccess;
import com.pnu.sursim.domain.user.entity.Gender;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SurveyRequest(
        String title,
        LocalDate startDate,
        LocalDate dueDate,
        Integer minAge,
        Integer maxAge,
        PublicAccess publicAccess,
        Gender gender,
        int points,
        List<QuestionRequest> questionList,
        ConsentInfoRequest consentInfo) {
}

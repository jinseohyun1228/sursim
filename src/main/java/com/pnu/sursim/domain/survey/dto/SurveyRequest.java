package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.PublicAccess;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SurveyRequest(
        String title,
        LocalDate dueDate,
        LocalDate startDate,
        PublicAccess publicAccess,
        List<QuestionRequest> questionRequestList) {
}

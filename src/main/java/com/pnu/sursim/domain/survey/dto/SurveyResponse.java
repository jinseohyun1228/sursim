package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.PublicAccess;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SurveyResponse(String title,
                             LocalDate startDate,
                             LocalDate dueDate,
                             PublicAccess publicAccess,
                             int points,
                             List<QuestionResponse> questionList) {

    public SurveyResponse(String title, LocalDate startDate, LocalDate dueDate, PublicAccess publicAccess, int points, List<QuestionResponse> questionList) {
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.publicAccess = publicAccess;
        this.points = points;
        this.questionList = questionList;
    }
}

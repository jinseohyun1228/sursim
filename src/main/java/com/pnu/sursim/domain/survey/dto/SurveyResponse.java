package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.AgeGroup;
import com.pnu.sursim.domain.survey.entity.PublicAccess;
import com.pnu.sursim.domain.survey.entity.RewardStatus;
import com.pnu.sursim.domain.user.entity.Gender;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SurveyResponse(Long id,
                             String title,
                             LocalDate startDate,
                             LocalDate dueDate,
                             AgeGroup ageGroup,
                             Integer minAge,
                             Integer maxAge,
                             PublicAccess publicAccess,
                             RewardStatus rewardStatus,
                             Gender gender,
                             int timeRequired,
                             int points,
                             List<QuestionResponse> questionList,
                             ConsentInfoResponse consentInfo) {

}

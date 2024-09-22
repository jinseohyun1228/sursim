package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.AgeGroup;
import com.pnu.sursim.domain.survey.entity.PublicAccess;
import com.pnu.sursim.domain.survey.entity.RewardStatus;
import com.pnu.sursim.domain.user.entity.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
public class SurveyResponse {
    protected Long id;
    protected String title;
    protected LocalDate startDate;
    protected LocalDate dueDate;
    protected AgeGroup ageGroup;
    protected Integer minAge;
    protected Integer maxAge;
    protected PublicAccess publicAccess;
    protected RewardStatus rewardStatus;
    protected Gender gender;
    protected int timeRequired;
    protected int points;
}

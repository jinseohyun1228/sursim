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
import java.util.List;


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
    protected List<QuestionResponse> questionList;
    protected ConsentInfoResponse consentInfo;

    public SurveyResponse(Long id, String title, LocalDate startDate, LocalDate dueDate, AgeGroup ageGroup, Integer minAge, Integer maxAge, PublicAccess publicAccess, RewardStatus rewardStatus, Gender gender, int timeRequired, int points, List<QuestionResponse> questionList, ConsentInfoResponse consentInfo) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.ageGroup = ageGroup;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.publicAccess = publicAccess;
        this.rewardStatus = rewardStatus;
        this.gender = gender;
        this.timeRequired = timeRequired;
        this.points = points;
        this.questionList = questionList;
        this.consentInfo = consentInfo;
    }
}

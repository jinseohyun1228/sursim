package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.*;
import com.pnu.sursim.domain.user.entity.Gender;
import jakarta.persistence.*;
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
public class SurveyWithRewardResponse extends SurveyResponse{

    private RewardResponse rewardResponse;

    public SurveyWithRewardResponse(Long id, String title, LocalDate startDate, LocalDate dueDate, AgeGroup ageGroup, Integer minAge, Integer maxAge, PublicAccess publicAccess, RewardStatus rewardStatus, Gender gender, int timeRequired, int points, List<QuestionResponse> questionList, ConsentInfoResponse consentInfo, RewardResponse rewardResponse) {
        super(id, title, startDate, dueDate, ageGroup, minAge, maxAge, publicAccess, rewardStatus, gender, timeRequired, points, questionList, consentInfo);
        this.rewardResponse = rewardResponse;
    }
}

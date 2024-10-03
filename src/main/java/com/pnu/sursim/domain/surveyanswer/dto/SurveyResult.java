package com.pnu.sursim.domain.surveyanswer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.AgeGroup;
import com.pnu.sursim.domain.survey.entity.PublicAccess;
import com.pnu.sursim.domain.survey.entity.RewardStatus;
import com.pnu.sursim.domain.survey.entity.Survey;
import com.pnu.sursim.domain.user.entity.Gender;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SurveyResult {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private AgeGroup ageGroup;
    private Integer minAge;
    private Integer maxAge;
    private PublicAccess publicAccess;
    private RewardStatus rewardStatus;
    private Gender gender;
    private List<QuestionResult> questionList;
    private long totalResponses;


    public SurveyResult(Survey targetSurvey, long totalResponses, List<QuestionResult> questionResults) {
        this.id = targetSurvey.getId();
        this.title = targetSurvey.getTitle();
        this.questionList = questionResults;
        this.startDate = targetSurvey.getStartDate();
        this.dueDate = targetSurvey.getDueDate();
        this.ageGroup = targetSurvey.getAgeGroup();
        this.minAge = targetSurvey.getMinAge();
        this.maxAge = targetSurvey.getMaxAge();
        this.publicAccess = targetSurvey.getPublicAccess();
        this.rewardStatus = targetSurvey.getRewardStatus();
        this.gender = targetSurvey.getGender();
        this.totalResponses = totalResponses;

    }

}

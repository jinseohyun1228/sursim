package com.pnu.sursim.domain.survey.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.RewardType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RewardResponse {
    private String title;
    private RewardType rewardType;
    private int count;
    private String rewardImg;

    @Builder
    public RewardResponse(String title, RewardType rewardType, int count, String rewardImg) {
        this.title = title;
        this.rewardType = rewardType;
        this.count = count;
        this.rewardImg = rewardImg;
    }
}

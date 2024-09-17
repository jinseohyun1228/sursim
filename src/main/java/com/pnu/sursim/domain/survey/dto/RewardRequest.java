package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.RewardType;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RewardRequest(String title,
                            RewardType rewardType,
                            int count) {
}

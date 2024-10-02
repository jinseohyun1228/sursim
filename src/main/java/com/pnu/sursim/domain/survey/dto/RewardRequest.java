package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.RewardType;
import org.springframework.web.bind.annotation.RequestParam;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RewardRequest(String title,
                            @RequestParam("reward_type") RewardType rewardType,
                            Integer count) {

    public boolean isEmpty() {
        // title이 null이거나 빈 문자열(""), rewardType과 count가 모두 null일 때 true 반환
        return (title == null || title.trim().isEmpty())
                && rewardType == null
                && count == null;
    }
}

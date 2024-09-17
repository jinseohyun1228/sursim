package com.pnu.sursim.domain.survey.dto;

import com.pnu.sursim.domain.survey.entity.RewardType;
import org.springframework.web.bind.annotation.RequestParam;

public record RewardRequest(String title,
                            @RequestParam("reward_type")  RewardType rewardType,
                            int count) {
}

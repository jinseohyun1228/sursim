package com.pnu.sursim.global.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.user.entity.UserInfoStatus;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AuthStatus(String token, UserInfoStatus status) {
}

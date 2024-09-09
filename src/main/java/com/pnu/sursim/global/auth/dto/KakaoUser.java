package com.pnu.sursim.global.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUser(Long id, Properties properties) {
    public String nickname() {
        return properties().nickname;
    }

    public String email() {
        return id.toString() + properties().nickname;
    }

    public String password() {
        return "password";
    }
    record Properties(String nickname) {
    }

}

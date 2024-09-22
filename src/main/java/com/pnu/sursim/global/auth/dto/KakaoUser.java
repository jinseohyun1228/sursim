package com.pnu.sursim.global.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUser(Long id, Properties properties, KakaoAccount kakaoAccount) {
    public String nickname() {
        return properties().nickname;
    }

    public String email() {
        return kakaoAccount().email;
    }

    public String password() {
        return "password";
    }


    record Properties(String nickname) {
    }

    record KakaoAccount(String email) {
    }
}

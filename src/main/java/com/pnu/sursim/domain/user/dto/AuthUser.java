package com.pnu.sursim.domain.user.dto;

import lombok.Getter;

@Getter
public class AuthUser {

    private String name;
    private String email;

    public AuthUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

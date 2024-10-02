package com.pnu.sursim.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.Region;
import com.pnu.sursim.domain.user.entity.User;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProfileResponse(String name,
                              String email,
                              LocalDate birthDate,
                              Gender gender,
                              int point) {

    public ProfileResponse(User user) {
        this(
                user.getName(),
                user.getEmail(),
                user.getBirthDate(),
                user.getGender(),
                user.getPoint()
        );
    }
}

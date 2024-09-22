package com.pnu.sursim.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.Region;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProfileResponse(String name,
                              String email,
                              String password,
                              LocalDate birthDate,
                              Gender gender,
                              Region region) {

    public ProfileResponse(UserVo user) {
        this(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getBirthDate(),
                user.getGender(),
                user.getRegion()
        );
    }
}

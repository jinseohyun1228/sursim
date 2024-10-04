package com.pnu.sursim.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.user.entity.Gender;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record JoinRequest(String name,
                          String email,
                          String password,
                          LocalDate birthDate,
                          Gender gender,
                          String phoneNumber) {


}

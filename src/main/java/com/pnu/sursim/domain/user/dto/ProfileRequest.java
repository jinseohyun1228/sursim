package com.pnu.sursim.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.user.entity.Gender;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProfileRequest(String name,
                             LocalDate birthDate,
                             Gender gender,
                             String phoneNumber) {

    public boolean existName() {
        return this.name != null && !this.name.isBlank();
    }

    public boolean existBirthDate() {
        return this.birthDate != null;
    }

    public boolean existGender() {
        return this.gender != null;
    }

    public boolean existPhoneNumber() {
        return this.phoneNumber != null;
    }

}

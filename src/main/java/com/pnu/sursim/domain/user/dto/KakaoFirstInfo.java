package com.pnu.sursim.domain.user.dto;

import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.Region;

import java.time.LocalDate;

public record KakaoFirstInfo(LocalDate birthDate,
                             Gender gender,
                             Region region) {
}

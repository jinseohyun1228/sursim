package com.pnu.sursim.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.Region;

import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record JoinRequest(String name,
                          String email,
                          String password,
                          LocalDate birthDate,
                          Gender gender,
                          Region region) {

//    public JoinRequest {
//        // 이름 검증
//        if (name == null || name.isBlank()) {
//            throw new CustomException(ErrorCode.EMPTY_VALUE_NOT_POSSIBLE);
//        }
//
//        // 이메일 검증
//        if (email == null || !email.contains("@")) {
//            throw new CustomException(ErrorCode.EMPTY_VALUE_NOT_POSSIBLE);
//        }
//
//        // 비밀번호 검증
//        if (password == null || password.length() < 8) {
//            throw new CustomException(ErrorCode.EMPTY_VALUE_NOT_POSSIBLE);
//        }
//
//        // 생년월일 검증
//        if (birthDate == null) {
//            throw new CustomException(ErrorCode.EMPTY_VALUE_NOT_POSSIBLE);
//        }
//
//        // 성별 검증
//        if (gender == null) {
//            throw new CustomException(ErrorCode.EMPTY_VALUE_NOT_POSSIBLE);
//        }
//
//        // 지역 검증
//        if (region == null) {
//            throw new CustomException(ErrorCode.EMPTY_VALUE_NOT_POSSIBLE);
//        }
//    }
}

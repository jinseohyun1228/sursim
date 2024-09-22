package com.pnu.sursim.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    FEMALE("여성"),
    MALE("남성"),
    NONE("무관");

    private final String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

    // JSON에서 한글 이름으로 매핑
    @JsonCreator
    public static Gender fromKoreanName(String koreanName) {
        for (Gender gender : Gender.values()) {
            if (gender.koreanName.equals(koreanName)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender: " + koreanName);
    }

    public String getKoreanName() {
        return koreanName;
    }

    // JSON 출력 시 한글 이름으로 출력
    @JsonValue
    public String toKoreanName() {
        return koreanName;
    }
}

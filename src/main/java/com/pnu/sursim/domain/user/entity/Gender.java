package com.pnu.sursim.domain.user.entity;

public enum Gender {
    FEMALE("여성"),
    MALE("남성"),
    NOT_SPECIFIED("공개하지 않음");

    private final String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}

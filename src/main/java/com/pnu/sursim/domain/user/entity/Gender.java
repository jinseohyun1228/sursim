package com.pnu.sursim.domain.user.entity;

public enum Gender{
    FEMALE("여성"),
    MALE("남성"),;

    private final String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}

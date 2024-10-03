package com.pnu.sursim.domain.survey.entity;


import com.fasterxml.jackson.annotation.JsonValue;

public enum Scale {
    ONE("1", "매우 그렇다."),
    TWO("2", "그렇다."),
    THREE("3", "보통이다."),
    FOUR("4", "그렇지 않다."),
    FIVE("5", "매우 그렇지 않다.");

    private final String value; // DB에 저장할 값
    private final String description; // 척도의 설명

    Scale(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


    public int getIntValue() {
        return Integer.valueOf(value);
    }

    public String getDescription() {
        return description;
    }
}

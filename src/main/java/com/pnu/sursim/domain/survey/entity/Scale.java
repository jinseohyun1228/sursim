package com.pnu.sursim.domain.survey.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Scale {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5");

    private final String value;

    Scale(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

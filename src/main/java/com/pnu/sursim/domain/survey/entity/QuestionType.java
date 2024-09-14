package com.pnu.sursim.domain.survey.entity;

public enum QuestionType {
    CHECK_CHOICE("체크박스"),
    SEMANTIC_RATINGS("의미분별척도"),
    LIKERT_SCORES("리커트척도"),
    MULTIPLE_CHOICE("객관식"),
    NUMERIC_RESPONSE("숫자응답"),
    PHONE_NUMBER("전화번호");

    private String description;

    QuestionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
package com.pnu.sursim.domain.survey.entity;

public enum QuestionType {
    CHECK_CHOICE("체크박스", 1), // 1분
    SEMANTIC_RATINGS("의미분별척도", 2), // 2분
    LIKERT_SCORES("리커트척도", 2), // 2분
    MULTIPLE_CHOICE("객관식", 1), // 1분
    NUMERIC_RESPONSE("숫자응답", 1), // 1분
    PHONE_NUMBER("전화번호", 1), // 1분
    SUBJECTIVE("주관식", 3), // 3분
    DESCRIPTIVE("서술형", 5); // 5분

    private final int timeTaken;
    private String description;

    QuestionType(String description, int minutes) {
        this.description = description;
        this.timeTaken = minutes;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public String getDescription() {
        return description;
    }
}
package com.pnu.sursim.domain.survey.entity;

public enum ConsentStatus {
    AGREED,       // 동의
    DISAGREED;     // 비동의

    public boolean isAgreed() {
        return (this == AGREED);
    }
}

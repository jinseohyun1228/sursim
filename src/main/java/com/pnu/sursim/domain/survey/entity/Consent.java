package com.pnu.sursim.domain.survey.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Consent {

    @Column
    private String collectionPurpose;   // 수집 목적
    @Column
    private String collectedData;       // 수집 정보
    @Column
    private String retentionPeriod;     // 보유 기간
    @Column
    private String contactInfo;         // 가능한 연락처
}

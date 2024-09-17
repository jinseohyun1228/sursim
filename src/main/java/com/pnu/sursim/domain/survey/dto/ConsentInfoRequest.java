package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ConsentInfoRequest(String collectionPurpose,
                                 String collectedData,
                                 String retentionPeriod,
                                 String contactInfo) {
}

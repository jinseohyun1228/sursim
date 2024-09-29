package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.Consent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
public class SpecSurveyResponse extends SurveyResponse {
    protected List<QuestionResponse> questionList;
    protected ConsentInfoResponse consentInfo;

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record ConsentInfoResponse(String collectionPurpose,
                                      String collectedData,
                                      String retentionPeriod,
                                      String contactInfo) {
        public ConsentInfoResponse(Consent consent) {
            this(consent.getCollectionPurpose(), consent.getCollectedData(), consent.getRetentionPeriod(), consent.getContactInfo());
        }
    }

}

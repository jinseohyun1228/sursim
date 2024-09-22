package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import com.pnu.sursim.domain.survey.entity.RequiredOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
public class SemanticQuestionResponse extends QuestionResponse {
    protected SemanticOptionResponse semanticOption;

    public SemanticQuestionResponse(Long id, int index, String text, QuestionType questionType, RequiredOption requiredOption, SemanticOptionResponse semanticOption) {
        super(id, index, text, questionType, requiredOption);
        this.semanticOption = semanticOption;
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record SemanticOptionResponse(String leftEnd,
                                         String rightEnd) {
    }
}

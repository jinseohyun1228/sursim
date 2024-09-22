package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import com.pnu.sursim.domain.survey.entity.RequiredOption;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
public class ChoiceQuestionResponse extends QuestionResponse {
    protected List<OptionResponse> optionResponses;


    public ChoiceQuestionResponse(Long id, int index, String text, QuestionType questionType, RequiredOption requiredOption, List<OptionResponse> optionResponses) {
        super(id, index, text, questionType, requiredOption);
        this.optionResponses = optionResponses;
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record OptionResponse(Long id, int index, String text) {
    }

}

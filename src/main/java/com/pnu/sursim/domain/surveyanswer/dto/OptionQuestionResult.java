package com.pnu.sursim.domain.surveyanswer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.Question;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OptionQuestionResult extends QuestionResult{

    private List<OptionResult> optionResultList;

    public OptionQuestionResult(Question question, long totalCount, List<OptionResult> optionResultList) {
        super(question.getId(), question.getIndex(), question.getText(), question.getQuestionType(), question.getRequiredOption(),totalCount);
        this.optionResultList = optionResultList;
    }
}

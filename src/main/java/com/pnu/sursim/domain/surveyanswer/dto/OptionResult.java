package com.pnu.sursim.domain.surveyanswer.dto;

import com.pnu.sursim.domain.survey.entity.QuestionOption;
import com.pnu.sursim.domain.survey.entity.Scale;
import lombok.Getter;

@Getter
public class OptionResult {
    private int index;
    private String text;
    private long count;

    public OptionResult(QuestionOption questionOption, long count) {
        this.index = questionOption.getIndex();
        this.text = questionOption.getText();
        this.count = count;
    }


    public OptionResult(Scale scale, long count) {
        this.index = scale.getIntValue();
        this.text = scale.getDescription();
        this.count = count;
    }
}

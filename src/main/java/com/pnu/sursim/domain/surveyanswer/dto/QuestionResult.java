package com.pnu.sursim.domain.surveyanswer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import com.pnu.sursim.domain.survey.entity.RequiredOption;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuestionResult {
    protected Long id;
    protected int index;                       //문항 번호
    protected String text;                      //문항 질문
    protected QuestionType questionType;        //문항타입
    protected RequiredOption requiredOption;    //필수여부
    private long totalCount;

    public QuestionResult(Long id, int index, String text, QuestionType questionType, RequiredOption requiredOption, long totalCount) {
        this.id = id;
        this.index = index;
        this.text = text;
        this.questionType = questionType;
        this.requiredOption = requiredOption;
        this.totalCount = totalCount;
    }

    public QuestionResult(Question question, long totalCount) {
        this.id = question.getId();
        this.index = question.getIndex();
        this.text = question.getText();
        this.questionType = question.getQuestionType();
        this.requiredOption = question.getRequiredOption();
        this.totalCount = totalCount;
    }
}

package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import com.pnu.sursim.domain.survey.entity.RequiredOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuperBuilder
public class QuestionResponse {
    protected Long id;
    protected int index;                       //문항 번호
    protected String text;                      //문항 질문
    protected QuestionType questionType;        //문항타입
    protected RequiredOption requiredOption;    //필수여부

    public QuestionResponse(Long id, int index, String text, QuestionType questionType, RequiredOption requiredOption) {
        this.id = id;
        this.index = index;
        this.text = text;
        this.questionType = questionType;
        this.requiredOption = requiredOption;
    }
}

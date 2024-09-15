package com.pnu.sursim.domain.survey.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import com.pnu.sursim.domain.survey.entity.RequiredOption;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record QuestionRequest(int number,       //문항 번호
                              String text,      //문항 질문
                              QuestionType questionType, //문항타입
                              RequiredOption requiredOption,    //필수여부
                              List<QuestionOptionRequest> questionOption,
                              SemanticOptionRequest semanticOption
) {


}

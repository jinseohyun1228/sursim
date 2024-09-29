package com.pnu.sursim.domain.surveyanswer.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import com.pnu.sursim.domain.survey.entity.Scale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AnswerRequest {
    private Long id;                            //문항 아이디
    private QuestionType questionType;          //문항 타입
    private Integer optionNumber;               //문항 번호
    private Scale scale;                      //척도
    private String text;                        //문항 텍스트
    private String phoneNumber;               //전화번호
    private List<Integer> checkNumberList;            //체크리스트

    public boolean validQuestionType(Question question) {
        return this.questionType == question.getQuestionType();
    }
}

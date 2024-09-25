package com.pnu.sursim.domain.surveyanswer.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class QuestionAnswerRequest {
    private Long id;                            //문항 아이디
    private QuestionType questionType;          //문항 타입
    private Integer optionNumber;               //문항 번호
    private String text;                        //문항 텍스트
    private String phoneNumber;                 //전화번호
    private List<Integer> checkList;            //체크리스트
}

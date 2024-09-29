package com.pnu.sursim.domain.surveyanswer.entity;

import com.pnu.sursim.domain.survey.entity.Question;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TextAnswer { // 주관식, 핸드폰 번호, 숫자(일단 숫자도 이걸로)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_answer_id")
    private SurveyAnswer surveyAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String value;
}

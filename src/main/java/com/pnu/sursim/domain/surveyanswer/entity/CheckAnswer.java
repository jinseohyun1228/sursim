package com.pnu.sursim.domain.surveyanswer.entity;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.QuestionOption;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CheckAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="survey_answer_id")
    private SurveyAnswer surveyAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ElementCollection
    private Set<String> selectedOptions; // 선택된 옵션들

    @ManyToOne
    @JoinColumn(name = "option_id")
    private QuestionOption option;
}

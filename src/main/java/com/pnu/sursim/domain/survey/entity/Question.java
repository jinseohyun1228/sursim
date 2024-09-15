package com.pnu.sursim.domain.survey.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    private String text;

    @Enumerated(EnumType.STRING)
    private RequiredOption requiredOption;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private int index;

    @Builder
    public Question(QuestionType questionType, int index, String text, RequiredOption requiredOption, Survey survey) {
        this.questionType = questionType;
        this.index = index;
        this.text = text;
        this.requiredOption = requiredOption;
        this.survey = survey;
    }
}

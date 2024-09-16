package com.pnu.sursim.domain.survey.entity;

import com.pnu.sursim.domain.survey.entity.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionOption {   //객관식응답(단일선택)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int index;

    private String text;


    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public QuestionOption(int index, String text, Question question) {
        this.index = index;
        this.text = text;
        this.question = question;
    }
}

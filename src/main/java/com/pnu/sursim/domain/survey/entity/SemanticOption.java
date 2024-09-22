package com.pnu.sursim.domain.survey.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SemanticOption {   //의미 분별 척도 응답의 정보

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String leftEnd;
    private String rightEnd;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public SemanticOption(String leftEnd, String rightEnd, Question question) {
        this.leftEnd = leftEnd;
        this.rightEnd = rightEnd;
        this.question = question;
    }
}

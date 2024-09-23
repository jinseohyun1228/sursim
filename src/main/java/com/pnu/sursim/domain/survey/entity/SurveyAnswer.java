package com.pnu.sursim.domain.survey.entity;

import com.pnu.sursim.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User answeringUser;

    private LocalDate writtenDate;

    //동의여부
    @Enumerated(EnumType.STRING)
    private ConsentStatus consentStatus;


    @Builder.Default
    @OneToMany(mappedBy = "question_answer")
    private List<QuestionAnswer> questionAnswerList = new ArrayList<>();
}

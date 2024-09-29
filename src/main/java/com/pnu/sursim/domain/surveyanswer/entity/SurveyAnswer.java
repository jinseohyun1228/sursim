package com.pnu.sursim.domain.surveyanswer.entity;

import com.pnu.sursim.domain.survey.entity.ConsentStatus;
import com.pnu.sursim.domain.survey.entity.Survey;
import com.pnu.sursim.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User answeringUser;

    private LocalDate writtenDate;

    //동의여부
    @Enumerated(EnumType.STRING)
    private ConsentStatus consentStatus;

}

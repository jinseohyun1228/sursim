package com.pnu.sursim.domain.survey.entity;

import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;

    //설문 제목
    private String title;

    //시작날짜
    private LocalDate startDate;

    //마감날짜
    private LocalDate dueDate;

    //가능한 나이 시작
    private int minAge;

    //가능한 나이 끝
    private int maxAge;

    @Enumerated(EnumType.STRING)
    private PublicAccess publicAccess;

    private int timeRequired;

    private int points;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    AgeGroup ageGroup;

    @Builder.Default
    @OneToMany(mappedBy = "survey")
    private List<Question> questions= new ArrayList<>();

}

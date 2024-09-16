package com.pnu.sursim.domain.survey.entity;

import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;

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

    @Builder
    public Survey(User creator, LocalDate startDate, LocalDate dueDate, int minAge, int maxAge, PublicAccess publicAccess, int timeRequired, int points, Gender gender) {
        this.creator = creator;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.publicAccess = publicAccess;
        this.timeRequired = timeRequired;
        this.points = points;
        this.gender = gender;
    }
}

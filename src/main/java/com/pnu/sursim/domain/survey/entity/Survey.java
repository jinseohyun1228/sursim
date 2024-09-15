package com.pnu.sursim.domain.survey.entity;

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

    private LocalDate dueDate;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private PublicAccess publicAccess;

    private int timeRequired;

    private int points;


    @Builder
    public Survey(User creator, LocalDate dueDate, LocalDate startDate, PublicAccess publicAccess, int timeRequired, int points) {
        this.creator = creator;
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.publicAccess = publicAccess;
        this.timeRequired = timeRequired;
        this.points = points;
    }
}

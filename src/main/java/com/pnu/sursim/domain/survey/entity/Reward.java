package com.pnu.sursim.domain.survey.entity;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private String title;

    private RewardType rewardType;

    private int count;

    private String rewardImg;

    @Builder
    public Reward(Survey survey, String title, RewardType rewardType, int count, String rewardImg) {
        this.survey = survey;
        this.title = title;
        this.rewardType = rewardType;
        this.count = count;
        this.rewardImg = rewardImg;
    }
}

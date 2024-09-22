package com.pnu.sursim.domain.survey.entity;

import com.pnu.sursim.domain.user.entity.Gender;
import com.pnu.sursim.domain.user.entity.User;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Survey {

    @Enumerated(EnumType.STRING)
    AgeGroup ageGroup;
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
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private RewardStatus rewardStatus = RewardStatus.NO_REWARD;

    @OneToOne
    private Reward reward;

    private String collectionPurpose;   // 수집 목적
    private String collectedData;       // 수집 정보
    private String retentionPeriod;     // 보유 기간
    private String contactInfo;         // 가능한 연락처

    public Boolean isCreator(User user) {
        return this.creator == user;
    }

    public Survey validateAddReward() {
        if (this.hasReward()) {
            throw new CustomException(ErrorCode.SURVEY_ALREADY_HAS_REWARDS);
        }
        return this;
    }

    public boolean hasReward() {
        return this.rewardStatus == RewardStatus.HAS_REWARD;
    }

    public void registerReward(Reward reward) {
        if (this.hasReward()) {
            throw new CustomException(ErrorCode.SURVEY_ALREADY_HAS_REWARDS);
        }
        this.reward = reward;
        this.rewardStatus = RewardStatus.HAS_REWARD;
    }


    public Survey validateCreator(User user) {
        if (this.creator != user) {
            throw new CustomException(ErrorCode.SURVEY_UNAUTHORIZED_USER);
        }
        return this;
    }
}

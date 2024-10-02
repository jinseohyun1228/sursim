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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //설문조사를 만든 유저
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User creator;

    //설문 제목
    private String title;

    //시작날짜
    private LocalDate startDate;

    //마감날짜
    @Column(name = "due_date")
    private LocalDate dueDate;

    //나이에 대한 응답 자격
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    //가능한 나이 시작
    private int minAge;

    //가능한 나이 끝
    private int maxAge;

    //성별에 대한 응답 자격
    @Enumerated(EnumType.STRING)
    private Gender gender;

    //응답 공개
    @Enumerated(EnumType.STRING)
    private PublicAccess publicAccess;

    //설문조사에 걸리는 시간
    private int timeRequired;

    //설문조사 응답시 받는 포인트
    private int points;

    //리워드 타입
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private RewardStatus rewardStatus = RewardStatus.NO_REWARD;

    //설문조사에 추가적인 리웓
    @OneToOne
    private Reward reward;

    @Embedded
    private Consent consent;

    public Boolean isCreator(User user) {
        return this.creator == user;
    }

    public Survey validateAddReward() {
        if (this.hasReward()) {
            throw new CustomException(ErrorCode.SURVEY_ALREADY_HAS_REWARDS);
        }
        return this;
    }

    //서베이에 리워드가 있는지 검사하는 메서드
    public boolean hasReward() {
        return this.rewardStatus == RewardStatus.HAS_REWARD;
    }

    //서베이에 리워드를 연결하는 편의메서드
    public void registerReward(Reward reward) {
        if (this.hasReward()) {
            throw new CustomException(ErrorCode.SURVEY_ALREADY_HAS_REWARDS);
        }
        this.reward = reward;
        this.rewardStatus = RewardStatus.HAS_REWARD;
    }


    //유저가 서베이의 크리에이터임
    public Survey verifySurveyCreator(User user) {
        if (this.creator != user) {
            throw new CustomException(ErrorCode.SURVEY_UNAUTHORIZED_USER);
        }
        return this;
    }

    //유저가 설문조사에 응답할 나이자격을 가지는지
    public boolean matchesAgeCriteria(int age) {
        if (this.ageGroup.equals(AgeGroup.ALL)) {
            return true;
        }
        return age >= this.minAge && age <= this.maxAge;
    }
    //유저가 설문조사에 응답할 나이자격을 가지는지
    public boolean matchesGenderCriteria(Gender gender) {
        return (this.gender.equals(Gender.NONE) || this.gender.equals(gender));
    }


}

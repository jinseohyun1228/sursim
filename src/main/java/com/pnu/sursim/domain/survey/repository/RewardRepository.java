package com.pnu.sursim.domain.survey.repository;

import com.pnu.sursim.domain.survey.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    Optional<Reward> findBySurveyId(Long aLong);
}

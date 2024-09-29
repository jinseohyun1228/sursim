package com.pnu.sursim.domain.survey.repository;

import com.pnu.sursim.domain.survey.entity.Survey;
import com.pnu.sursim.domain.user.entity.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT s FROM Survey s WHERE " +
            "(YEAR(CURRENT_DATE) - YEAR(:birthDate)) >= s.minAge AND " +
            "(YEAR(CURRENT_DATE) - YEAR(:birthDate)) <= s.maxAge AND " +
            "(s.gender = :gender OR s.gender = com.pnu.sursim.domain.user.entity.Gender.NONE) AND " +
            "s.dueDate >= CURRENT_DATE")
    Page<Survey> findAllByAgeAndGender(LocalDate birthDate, Gender gender, Pageable pageable);


    @Query("SELECT s FROM Survey s WHERE " +
            "(YEAR(CURRENT_DATE) - YEAR(:birthDate)) >= s.minAge AND " +
            "(YEAR(CURRENT_DATE) - YEAR(:birthDate)) <= s.maxAge AND " +
            "(s.gender = :gender OR s.gender = com.pnu.sursim.domain.user.entity.Gender.NONE) AND " +
            "s.rewardStatus = com.pnu.sursim.domain.survey.entity.RewardStatus.HAS_REWARD AND " +
            "s.dueDate >= CURRENT_DATE")
    Page<Survey> findAllByAgeAndGenderAndHasReward(LocalDate birthDate, Gender gender, Pageable pageable);


    @Query("SELECT s FROM Survey s WHERE s.dueDate >= CURRENT_DATE")
    Page<Survey> findAllByDueDateAfter(Pageable pageable);

}

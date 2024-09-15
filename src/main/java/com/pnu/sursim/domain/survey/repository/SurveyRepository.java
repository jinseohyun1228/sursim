package com.pnu.sursim.domain.survey.repository;

import com.pnu.sursim.domain.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}

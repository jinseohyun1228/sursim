package com.pnu.sursim.domain.survey.repository;

import com.pnu.sursim.domain.survey.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
}

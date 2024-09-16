package com.pnu.sursim.domain.survey.repository;

import com.pnu.sursim.domain.survey.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {

    List<QuestionOption> findAllByQuestionIdOrderByIndexAsc(Long id);
}

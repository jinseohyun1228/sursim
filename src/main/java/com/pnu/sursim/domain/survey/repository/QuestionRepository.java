package com.pnu.sursim.domain.survey.repository;

import com.pnu.sursim.domain.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllBySurveyIdOrderByIndexAsc(Long id);
}

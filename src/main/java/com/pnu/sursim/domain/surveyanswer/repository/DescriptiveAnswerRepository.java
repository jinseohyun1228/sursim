package com.pnu.sursim.domain.surveyanswer.repository;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.surveyanswer.entity.DescriptiveAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptiveAnswerRepository extends JpaRepository<DescriptiveAnswer, Long> {
    long countByQuestion(Question question);
}

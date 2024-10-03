package com.pnu.sursim.domain.surveyanswer.repository;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.surveyanswer.entity.TextAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextAnswerRepository extends JpaRepository<TextAnswer, Long> {
    long countByQuestion(Question question);

}

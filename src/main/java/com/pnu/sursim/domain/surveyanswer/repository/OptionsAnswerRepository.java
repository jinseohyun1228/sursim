package com.pnu.sursim.domain.surveyanswer.repository;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.QuestionOption;
import com.pnu.sursim.domain.surveyanswer.entity.OptionsAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionsAnswerRepository extends JpaRepository<OptionsAnswer, Long> {

    @Query("SELECT COUNT(o) FROM OptionsAnswer o WHERE o.questionOption = :questionOption")
    long countByQuestionOption(@Param("questionOption") QuestionOption questionOption);

    long countByQuestion(Question question);
}

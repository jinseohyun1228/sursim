package com.pnu.sursim.domain.surveyanswer.repository;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.Scale;
import com.pnu.sursim.domain.surveyanswer.entity.ScaleAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface ScaleAnswerRepository extends JpaRepository<ScaleAnswer, Long> {

    // 특정 질문과 스케일 값으로 개수를 반환하는 메서드
    @Query("SELECT COUNT(s) FROM ScaleAnswer s WHERE s.question = :question AND s.scale = :scale")
    long countByQuestionAndScale(@Param("question") Question question, @Param("scale") Scale scale);

    // 특정 질문에 대한 전체 ScaleAnswer 개수를 반환하는 메서드
    long countByQuestion(Question question);

    // 특정 질문에 대해 각 스케일 값의 개수를 반환하는 메서드
    @Query("SELECT s.scale, COUNT(s) FROM ScaleAnswer s WHERE s.question = :question GROUP BY s.scale")
    Map<Scale, Long> countByQuestionGroupedByScale(@Param("question") Question question);

}

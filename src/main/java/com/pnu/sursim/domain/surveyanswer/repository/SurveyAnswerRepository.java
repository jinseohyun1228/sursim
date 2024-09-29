package com.pnu.sursim.domain.surveyanswer.repository;

import com.pnu.sursim.domain.surveyanswer.entity.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {

    boolean existsBySurveyIdAndAnsweringUserId(Long surveyId, Long answerUserId);

}

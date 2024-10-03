package com.pnu.sursim.domain.surveyanswer.repository;

import com.pnu.sursim.domain.survey.entity.Survey;
import com.pnu.sursim.domain.surveyanswer.entity.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {

    boolean existsBySurveyIdAndAnsweringUserId(Long surveyId, Long answerUserId);

    // 특정 서베이에 대한 응답 개수를 반환하는 메서드
    long countBySurvey(Survey survey);
}

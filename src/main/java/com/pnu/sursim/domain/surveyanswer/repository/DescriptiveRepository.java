package com.pnu.sursim.domain.surveyanswer.repository;

import com.pnu.sursim.domain.surveyanswer.entity.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptiveRepository extends JpaRepository<SurveyAnswer, Long> {

}

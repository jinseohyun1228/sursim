package com.pnu.sursim.domain.surveyanswer.repository;

import com.pnu.sursim.domain.surveyanswer.entity.DescriptiveAnswer;
import com.pnu.sursim.domain.surveyanswer.entity.OptionsAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionsAnswerRepository extends JpaRepository<OptionsAnswer, Long> {

}

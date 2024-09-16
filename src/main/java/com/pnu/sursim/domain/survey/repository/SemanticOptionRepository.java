package com.pnu.sursim.domain.survey.repository;

import com.pnu.sursim.domain.survey.entity.SemanticOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SemanticOptionRepository extends JpaRepository<SemanticOption, Long> {
    Optional<SemanticOption> findByQuestionId(Long id);
}

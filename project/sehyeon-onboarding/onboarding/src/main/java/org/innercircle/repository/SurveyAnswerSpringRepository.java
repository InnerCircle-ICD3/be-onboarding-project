package org.innercircle.repository;


import org.innercircle.entity.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerSpringRepository extends JpaRepository<SurveyAnswer, Long> {
}

package com.survey.application.repository;

import com.survey.domain.surveyResponse.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
}

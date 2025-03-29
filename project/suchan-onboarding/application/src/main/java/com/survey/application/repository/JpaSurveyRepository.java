package com.survey.application.repository;

import com.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSurveyRepository extends JpaRepository<Survey, Long> {

}

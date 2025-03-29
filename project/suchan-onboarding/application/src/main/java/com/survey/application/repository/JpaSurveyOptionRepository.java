package com.survey.application.repository;

import com.survey.domain.survey.Survey;
import com.survey.domain.survey.SurveyOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSurveyOptionRepository extends JpaRepository<SurveyOption, Long> {
    List<SurveyOption> findBySurvey(Survey survey);
}

package com.survey.domain.survey.repository;

import com.survey.domain.survey.Survey;

import java.util.Optional;

public interface SurveyRepository {

    Survey save(Survey survey);

    Optional<Survey> findById(Long surveyId);

    Optional<Survey> findCompleteSurvey(Long surveyId);
}

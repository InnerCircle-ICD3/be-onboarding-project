package com.survey.domain.repository;

import com.survey.domain.Survey;

import java.util.Optional;

public interface SurveyRepository {

    Survey save(Survey survey);

    Optional<Survey> findById(Long surveyId);

    Optional<Survey> findCompleteSurveyFetchJoin(Long surveyId);
}

package com.survey.domain.surveyResponse.repository;

import com.survey.domain.surveyResponse.SurveyResponse;

import java.util.List;

public interface SurveyResponseRepository {
    void save(SurveyResponse surveyResponse);

    List<SurveyResponse> findBySurveyIdFetchJoin(Long surveyId);
}

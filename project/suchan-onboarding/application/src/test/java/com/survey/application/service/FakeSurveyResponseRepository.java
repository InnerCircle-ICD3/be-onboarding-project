package com.survey.application.service;

import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.repository.SurveyResponseRepository;

import java.util.HashMap;
import java.util.List;

public class FakeSurveyResponseRepository implements SurveyResponseRepository {

    private final HashMap<Long, SurveyResponse> storage;

    public FakeSurveyResponseRepository(HashMap<Long, SurveyResponse> storage) {
        this.storage = storage;
    }

    @Override
    public void save(SurveyResponse surveyResponse) {
        // TODO
    }

    @Override
    public List<SurveyResponse> findBySurveyIdFetchJoin(Long surveyId) {
        // TODO
        return null;
    }
}

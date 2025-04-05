package com.survey.application.service;

import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.repository.SurveyResponseRepository;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FakeSurveyResponseRepository implements SurveyResponseRepository {

    private final HashMap<Long, SurveyResponse> storage;
    private Long id = 0L;

    public FakeSurveyResponseRepository() {
        this.storage = new HashMap<>();
    }

    public FakeSurveyResponseRepository(HashMap<Long, SurveyResponse> storage) {
        this.storage = storage;
    }

    @Override
    public void save(SurveyResponse surveyResponse) {
        if (surveyResponse.getId() == null) {
            id++;
            storage.put(id, surveyResponse);
        } else {
            storage.put(surveyResponse.getId(), surveyResponse);
        }
    }

    @Override
    public List<SurveyResponse> findBySurveyIdFetchJoin(Long surveyId) {
        return storage.values().stream()
                .filter(response -> response.getSurveyId().equals(surveyId))
                .collect(Collectors.toList());
    }

}
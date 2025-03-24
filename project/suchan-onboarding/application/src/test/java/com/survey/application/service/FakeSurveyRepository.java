package com.survey.application.service;

import com.survey.domain.Survey;
import com.survey.domain.repository.SurveyRepository;

import java.util.HashMap;
import java.util.Map;

public class FakeSurveyRepository implements SurveyRepository {

    private final Map<Long, Survey> storage;
    private long idIndex;
    private int callCounter;

    public FakeSurveyRepository(Map<Long, Survey> storage) {
        this.storage = storage;
        this.idIndex = 0;
        this.callCounter = 0;
    }

    @Override
    public Survey save(Survey survey) {
        idIndex++;
        survey.changeId(idIndex);
        storage.put(idIndex, survey);
        callCounter++;
        return survey;
    }

    public Survey findById(long id) {
        callCounter++;
        return storage.get(id);
    }

    public int getCallCounter() {
        return callCounter;
    }

}

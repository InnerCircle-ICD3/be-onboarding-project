package com.survey.test;

import com.survey.domain.survey.Survey;
import com.survey.domain.survey.repository.SurveyRepository;

import java.util.Map;
import java.util.Optional;

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

    @Override
    public Optional<Survey> findById(Long surveyId) {
        callCounter++;
        return Optional.ofNullable(storage.get(surveyId));
    }

    @Override
    public Optional<Survey> findCompleteSurvey(Long surveyId) {
        callCounter++;
        Survey survey = storage.get(surveyId);

        if (survey == null) {
            return Optional.empty();
        }

        return Optional.of(survey);
    }

    public int getCallCounter() {
        return callCounter;
    }

}

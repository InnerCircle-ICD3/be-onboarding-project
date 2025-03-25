package com.survey.application.service;

import com.survey.application.request.CreateSurveyRequest;
import com.survey.application.request.UpdateSurveyRequest;
import com.survey.domain.Survey;
import com.survey.domain.repository.SurveyRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public void createSurvey(CreateSurveyRequest request) {
        Survey survey = request.create();
        surveyRepository.save(survey);
    }

    public void updateSurvey(@Valid UpdateSurveyRequest request) {
        Survey survey = request.create();
        survey.update(survey);
    }
}

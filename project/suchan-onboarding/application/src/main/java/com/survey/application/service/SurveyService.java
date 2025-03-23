package com.survey.application.service;

import com.survey.application.dto.CreateSurveyRequest;
import com.survey.domain.Survey;
import com.survey.domain.repository.SurveyRepository;
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
}

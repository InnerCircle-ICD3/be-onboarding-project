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
    private static final String SURVEY_NOT_FOUND_EXCEPTION = "설문 조사를 찾을 수 없습니다.";

    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public void registerSurvey(CreateSurveyRequest request) {
        Survey survey = request.create();
        surveyRepository.save(survey);
    }

    // 변경/추가/삭제 모두 처리
    public void changeSurvey(@Valid UpdateSurveyRequest request) {
        Survey existingSurvey = surveyRepository.findById(request.getSurveyId())
                .orElseThrow(() -> new IllegalArgumentException(SURVEY_NOT_FOUND_EXCEPTION));
        Survey updatedSurvey = request.create();
        existingSurvey.modify(updatedSurvey);
    }
}

package com.survey.application.service;

import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.dto.request.ResponseSurveyRequest;
import com.survey.application.dto.request.UpdateSurveyRequest;
import com.survey.domain.survey.Survey;
import com.survey.domain.survey.repository.SurveyRepository;
import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.repository.SurveyResponseRepository;
import com.survey.domain.surveyResponse.service.SurveyResponseValidationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SurveyService {
    private static final String SURVEY_NOT_FOUND_EXCEPTION = "설문 조사를 찾을 수 없습니다.";

    private final SurveyRepository surveyRepository;
    private final SurveyResponseValidationService surveyResponseValidationService;
    private final SurveyResponseRepository surveyResponseRepository;

    public SurveyService(SurveyRepository surveyRepository,
                         SurveyResponseValidationService surveyResponseValidationService,
                         SurveyResponseRepository surveyResponseRepository) {
        this.surveyRepository = surveyRepository;
        this.surveyResponseValidationService = surveyResponseValidationService;
        this.surveyResponseRepository = surveyResponseRepository;
    }

    public Long registerSurvey(CreateSurveyRequest request) {
        Survey survey = request.create();
        return surveyRepository.save(survey).getId();
    }

    // 변경/추가/삭제 모두 처리
    public void changeSurvey(UpdateSurveyRequest request) {
        Survey existingSurvey = surveyRepository.findCompleteSurvey(request.getSurveyId())
                .orElseThrow(() -> new IllegalArgumentException(SURVEY_NOT_FOUND_EXCEPTION));

        Survey updatedSurvey = request.create();
        existingSurvey.modify(updatedSurvey);
    }

    public void responseSurvey(ResponseSurveyRequest request) {
        SurveyResponse surveyResponse = request.create();
        surveyResponseValidationService.validateSurveyResponse(surveyResponse);
        surveyResponseRepository.save(surveyResponse);
    }
}

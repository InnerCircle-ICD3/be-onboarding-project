package com.survey.application.service;

import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.dto.request.UpdateSurveyRequest;
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

    public Long registerSurvey(CreateSurveyRequest request) {
        Survey survey = request.create();
        return surveyRepository.save(survey).getId();
    }

    // 변경/추가/삭제 모두 처리
    public void changeSurvey(UpdateSurveyRequest request) {
        Survey existingSurvey = surveyRepository.findById(request.getSurveyId())
                .orElseThrow(() -> new IllegalArgumentException(SURVEY_NOT_FOUND_EXCEPTION));
        Survey updatedSurvey = request.create();
        existingSurvey.modify(updatedSurvey);
    }
}

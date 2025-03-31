package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.SurveyResponse;
import com.innercircle.yeonwoo_onboarding.repository.SurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyResponseService {
    private final SurveyResponseRepository surveyResponseRepository;

    public List<SurveyResponse> findBySurveyId(String surveyId) {
        return surveyResponseRepository.findBySurveyId(surveyId);
    }

    @Transactional
    public SurveyResponse createResponse(SurveyResponse response) {
        return surveyResponseRepository.save(response);
    }

    public SurveyResponse findById(String id) {
        return surveyResponseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SurveyResponse not found with id: " + id));
    }
}
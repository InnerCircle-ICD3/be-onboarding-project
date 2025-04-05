package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItemResponse;
import com.innercircle.yeonwoo_onboarding.repository.SurveyItemResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyItemResponseService {
    private final SurveyItemResponseRepository surveyItemResponseRepository;

    public List<SurveyItemResponse> findBySurveyResponseId(String surveyResponseId) {
        return surveyItemResponseRepository.findBySurveyResponseId(surveyResponseId);
    }

    @Transactional
    public SurveyItemResponse createResponse(SurveyItemResponse response) {
        return surveyItemResponseRepository.save(response);
    }
}
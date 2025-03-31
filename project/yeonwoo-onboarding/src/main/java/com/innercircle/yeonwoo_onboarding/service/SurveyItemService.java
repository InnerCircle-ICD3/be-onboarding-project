package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItem;
import com.innercircle.yeonwoo_onboarding.repository.SurveyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyItemService {
    private final SurveyItemRepository surveyItemRepository;

    public List<SurveyItem> findBySurveyId(String surveyId) {
        return surveyItemRepository.findBySurveyId(surveyId);
    }

    public SurveyItem findById(String id) {
        return surveyItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SurveyItem not found with id: " + id));
    }

    @Transactional
    public SurveyItem createSurveyItem(SurveyItem surveyItem) {
        return surveyItemRepository.save(surveyItem);
    }

    @Transactional
    public void deleteSurveyItem(String id) {
        surveyItemRepository.deleteById(id);
    }
}
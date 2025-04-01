package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItemOption;
import com.innercircle.yeonwoo_onboarding.repository.SurveyItemOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyItemOptionService {
    private final SurveyItemOptionRepository surveyItemOptionRepository;

    public List<SurveyItemOption> findBySurveyItemId(String surveyItemId) {
        return surveyItemOptionRepository.findBySurveyItemId(surveyItemId);
    }

    @Transactional
    public SurveyItemOption createOption(SurveyItemOption option) {
        return surveyItemOptionRepository.save(option);
    }

    @Transactional
    public void deleteOption(String id) {
        surveyItemOptionRepository.deleteById(id);
    }
}
package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.Survey;
import com.innercircle.yeonwoo_onboarding.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public List<Survey> findAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey findSurveyById(String id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with id: " + id));
    }

    @Transactional
    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Transactional
    public Survey updateSurvey(String id, Survey updatedSurvey) {
        Survey existingSurvey = surveyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with id: " + id));
        
        existingSurvey.setName(updatedSurvey.getName());
        existingSurvey.setDescription(updatedSurvey.getDescription());
        
        return surveyRepository.save(existingSurvey);
    }

    @Transactional
    public void deleteSurvey(String id) {
        Survey survey = surveyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Survey not found with id: " + id));
        surveyRepository.deleteById(id);
    }
}
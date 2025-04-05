package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.Survey;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItem;
import com.innercircle.yeonwoo_onboarding.domain.SurveyItemOption;
import com.innercircle.yeonwoo_onboarding.domain.enums.InputType;
import com.innercircle.yeonwoo_onboarding.repository.SurveyRepository;
import com.innercircle.yeonwoo_onboarding.dto.SurveyCreateDto;
import com.innercircle.yeonwoo_onboarding.dto.SurveyItemCreateDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    private final SurveyItemService surveyItemService;
    private final SurveyItemOptionService surveyItemOptionService;


    public List<Survey> findAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey findSurveyById(String id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found with id: " + id));
    }

    @Transactional

    public Survey createSurvey(SurveyCreateDto surveyDto) {
        // 설문조사 이름, 설명 생성 후 저장
        Survey survey = new Survey();
        survey.setName(surveyDto.getName());
        survey.setDescription(surveyDto.getDescription());
        Survey savedSurvey = surveyRepository.save(survey);

        // 설문조사 항목 생성
        if (surveyDto.getItems() != null) {
            for (SurveyItemCreateDto itemDto : surveyDto.getItems()) {
                SurveyItem item = new SurveyItem();
                item.setSurvey(savedSurvey);
                item.setName(itemDto.getName());
                item.setDescription(itemDto.getDescription());
                item.setInputType(itemDto.getInputType());
                item.setRequired(itemDto.isRequired());
                
                SurveyItem savedItem = surveyItemService.createSurveyItem(item);

                // 인풋타입이 SINGLE/MULTIPLE 일 경우 설문조사 항목 옵션 생성
                if ((itemDto.getInputType() == InputType.SINGLE || 
                     itemDto.getInputType() == InputType.MULTIPLE) && 
                     itemDto.getOptions() != null) {
                    for (String optionText : itemDto.getOptions()) {
                        SurveyItemOption option = new SurveyItemOption();
                        option.setSurveyItem(savedItem);
                        option.setOptionText(optionText);
                        surveyItemOptionService.createOption(option);
                    }
                }
            }
        }

        return savedSurvey;
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
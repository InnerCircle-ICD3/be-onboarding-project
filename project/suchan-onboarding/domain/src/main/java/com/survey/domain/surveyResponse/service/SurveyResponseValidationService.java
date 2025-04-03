package com.survey.domain.surveyResponse.service;

import com.survey.domain.survey.*;
import com.survey.domain.survey.repository.SurveyRepository;
import com.survey.domain.surveyResponse.ChoiceResponse;
import com.survey.domain.surveyResponse.SurveyOptionResponse;
import com.survey.domain.surveyResponse.SurveyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyResponseValidationService {
    private static final String SURVEY_NOT_FOUND_EXCEPTION_MESSAGE = "설문조사를 찾을 수 없습니다.";
    private static final String SURVEY_VERSION_EXCEPTION_MESSAGE = "설문 조사 버전과 실제 설문 받은 항목에 대한 응답 버전이 동일하지 않습니다.";
    private static final String NECESSARY_OPTION_SKIP_EXCEPTION_MESSAGE = "필수 항목에 대한 응답이 누락되었습니다.";
    private static final String INVALID_SURVEY_OPTION_EXCEPTION_MESSAGE = "유효하지 않은 설문 항목입니다.";
    private static final String WRONG_TEXT_RESPONSE_EXCEPTION_MESSAGE = "텍스트 응답이 아닌 설문 항목입니다.";
    private static final String WRONG_CHOICE_RESPONSE_EXCEPTION_MESSAGE = "선택형 응답이 아닌 설문 항목입니다.";
    private static final String INVALID_SELECTED_OPTION_EXCEPTION_MESSAGE = "유효하지 않은 선택지입니다";
    private static final String MULTIPLE_CHOICE_EXCEPTION_MESSAGE = "단일 선택 항목에는 하나의 옵션만 선택해야 합니다.";

    private final SurveyRepository surveyRepository;

    public SurveyResponseValidationService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public void validateSurveyResponse(SurveyResponse surveyResponse) {
        Survey survey = surveyRepository.findById(surveyResponse.getSurveyId())
                .orElseThrow(() -> new IllegalArgumentException(SURVEY_NOT_FOUND_EXCEPTION_MESSAGE));

        validateSurveyVersion(survey, surveyResponse);
        validateRequiredOptionsAnswered(survey, surveyResponse.getSurveyOptionResponses());

        for (SurveyOptionResponse optionResponse : surveyResponse.getSurveyOptionResponses()) {
            validateOptionResponse(survey, optionResponse);
        }
    }

    private void validateSurveyVersion(Survey survey, SurveyResponse surveyResponse) {
        long version = survey.getVersion();
        if (!surveyResponse.isSameSurveyVersion(version)) {
            throw new IllegalArgumentException(SURVEY_VERSION_EXCEPTION_MESSAGE);
        }
    }

    private void validateRequiredOptionsAnswered(Survey survey, List<SurveyOptionResponse> optionResponses) {
        List<Long> requiredOptionIds = survey.getSurveyOptions().stream()
                .filter(SurveyOption::isNecessary)
                .map(SurveyOption::getId)
                .toList();

        List<Long> respondedOptionIds = optionResponses.stream()
                .map(SurveyOptionResponse::getSurveyOptionId)
                .toList();

        for (Long requiredOptionId : requiredOptionIds) {
            if (!respondedOptionIds.contains(requiredOptionId)) {
                throw new IllegalArgumentException(NECESSARY_OPTION_SKIP_EXCEPTION_MESSAGE);
            }
        }
    }

    private void validateOptionResponse(Survey survey, SurveyOptionResponse optionResponse) {
        SurveyOption surveyOption = survey.getSurveyOptions().stream()
                .filter(o -> o.getId().equals(optionResponse.getSurveyOptionId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_SURVEY_OPTION_EXCEPTION_MESSAGE));

        InputForm inputForm = surveyOption.getInputForm();

        validateResponseType(inputForm, optionResponse);

        if (optionResponse.hasChoiceResponse()) {
            validateChoiceResponseOptions(inputForm, optionResponse.getChoiceResponse());
        }
    }

    private void validateResponseType(InputForm inputForm, SurveyOptionResponse optionResponse) {
        if (optionResponse.hasTextResponse() && !inputForm.hasTextInputForm()) {
            throw new IllegalArgumentException(WRONG_TEXT_RESPONSE_EXCEPTION_MESSAGE);
        }

        if (optionResponse.hasChoiceResponse() && !inputForm.hasChoiceInputForm()) {
            throw new IllegalArgumentException(WRONG_CHOICE_RESPONSE_EXCEPTION_MESSAGE);
        }
    }

    private void validateChoiceResponseOptions(InputForm inputForm, ChoiceResponse choiceResponse) {
        ChoiceInputForm choiceInputForm = inputForm.getChoiceInputForm();
        List<String> selectedOptions = choiceResponse.getSelectedOptions();

        List<String> validOptions = choiceInputForm.getInputOptions().stream()
                .map(InputOption::getOption)
                .toList();

        for (String selected : selectedOptions) {
            if (!validOptions.contains(selected)) {
                throw new IllegalArgumentException(INVALID_SELECTED_OPTION_EXCEPTION_MESSAGE);
            }
        }

        if (choiceInputForm.getChoiceType() == ChoiceType.SINGLE && selectedOptions.size() > 1) {
            throw new IllegalArgumentException(MULTIPLE_CHOICE_EXCEPTION_MESSAGE);
        }
    }
}
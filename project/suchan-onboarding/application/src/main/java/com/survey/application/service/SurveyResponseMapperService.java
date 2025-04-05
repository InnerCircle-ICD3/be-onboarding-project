package com.survey.application.service;

import com.survey.application.dto.response.GetAllSurveyResultResponse;
import com.survey.application.dto.response.GetAllSurveyResultResponse.ChoiceResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.InputFormResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.SurveyOptionResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.TextResultDto;
import com.survey.domain.survey.*;
import com.survey.domain.surveyResponse.ChoiceResponse;
import com.survey.domain.surveyResponse.SurveyOptionResponse;
import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.TextResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SurveyResponseMapperService {
    private static final String UNSUPPORTED_SURVEY_OPTION_EXCEPTION_MESSAGE = "설문지에 존재하지 않는 항목에 대한 응답입니다 ";
    private static final String NECESSARY_SURVEY_OPTION_EXCEPTION_MESSAGE = "필수 설문 항목에 대한 응답이 없습니다 ";
    private static final String UNSUPPORTED_RESPONSE_TYPE_EXCEPTION_MESSAGE = "지원되지 않는 응답 형식입니다. 설문 항목 ID ";

    public GetAllSurveyResultResponse generate(Survey survey, SurveyResponse surveyResponse) {
        validateResponsesExistInSurvey(survey, surveyResponse);

        List<SurveyOptionResultDto> surveyOptionResultDtos = new ArrayList<>();

        for (SurveyOption surveyOption : survey.getSurveyOptions()) {
            addSurveyOptionResultDto(surveyResponse, surveyOption, surveyOptionResultDtos);
        }

        return new GetAllSurveyResultResponse(
                survey.getId(),
                survey.getVersion(),
                survey.getTitle(),
                survey.getDescription(),
                surveyOptionResultDtos
        );
    }

    private void validateResponsesExistInSurvey(Survey survey, SurveyResponse surveyResponse) {
        Set<Long> surveyOptionIds = new HashSet<>();
        for (SurveyOption option : survey.getSurveyOptions()) {
            surveyOptionIds.add(option.getId());
        }

        for (SurveyOptionResponse response : surveyResponse.getSurveyOptionResponses()) {
            if (!surveyOptionIds.contains(response.getSurveyOptionId())) {
                throw new IllegalArgumentException(UNSUPPORTED_SURVEY_OPTION_EXCEPTION_MESSAGE + response.getSurveyOptionId());
            }
        }
    }

    private void addSurveyOptionResultDto(SurveyResponse surveyResponse, SurveyOption surveyOption, List<SurveyOptionResultDto> surveyOptionResultDtos) {
        if (!surveyOption.isActivated()) {
            return;
        }

        for (SurveyOptionResponse surveyOptionRes : surveyResponse.getSurveyOptionResponses()) {
            if (surveyOptionRes.getSurveyOptionId().equals(surveyOption.getId())) {
                surveyOptionResultDtos.add(createSurveyOptionResult(surveyOption, surveyOptionRes));
                return;
            }
        }

        if (surveyOption.isNecessary()) {
            throw new IllegalArgumentException(NECESSARY_SURVEY_OPTION_EXCEPTION_MESSAGE + surveyOption.getId());
        }
    }

    private SurveyOptionResultDto createSurveyOptionResult(SurveyOption surveyOption, SurveyOptionResponse surveyOptionRes) {
        return new SurveyOptionResultDto(
                surveyOption.getId(),
                surveyOption.getTitle(),
                surveyOption.getDescription(),
                surveyOption.isNecessary(),
                createInputFormResult(surveyOption.getInputForm(), surveyOptionRes)
        );
    }

    private InputFormResultDto createInputFormResult(InputForm inputForm, SurveyOptionResponse surveyOptionRes) {
        if (surveyOptionRes.hasTextResponse()) {
            TextInputForm textInputForm = inputForm.getTextInputForm();
            return new InputFormResultDto(
                    inputForm.getId(),
                    inputForm.getQuestion(),
                    createTextResult(textInputForm, surveyOptionRes.getTextResponse())
            );
        } else if (surveyOptionRes.hasChoiceResponse()) {
            ChoiceInputForm choiceInputForm = inputForm.getChoiceInputForm();
            return new InputFormResultDto(
                    inputForm.getId(),
                    inputForm.getQuestion(),
                    createChoiceResult(choiceInputForm, surveyOptionRes.getChoiceResponse())
            );
        } else {
            throw new IllegalArgumentException(UNSUPPORTED_RESPONSE_TYPE_EXCEPTION_MESSAGE + surveyOptionRes.getSurveyOptionId());
        }
    }

    private TextResultDto createTextResult(TextInputForm textInputForm, TextResponse textResponse) {
        return new TextResultDto(
                textInputForm.getId(),
                textInputForm.getTextType().getName(),
                textResponse.getAnswer()
        );
    }

    private ChoiceResultDto createChoiceResult(ChoiceInputForm choiceInputForm, ChoiceResponse choiceResponse) {
        return new ChoiceResultDto(
                choiceInputForm.getId(),
                choiceInputForm.getChoiceType().getName(),
                choiceInputForm.getInputOptions().stream()
                        .map(InputOption::getOption)
                        .toList(),
                choiceResponse.getSelectedOptions()
        );
    }
}
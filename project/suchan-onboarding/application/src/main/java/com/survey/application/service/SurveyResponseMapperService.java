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
import java.util.List;

@Service
public class SurveyResponseMapperService {

    public GetAllSurveyResultResponse generate(Survey survey, SurveyResponse surveyResponse) {
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

    private void addSurveyOptionResultDto(SurveyResponse surveyResponse, SurveyOption surveyOption, List<SurveyOptionResultDto> surveyOptionResultDtos) {
        for (SurveyOptionResponse surveyOptionRes : surveyResponse.getSurveyOptionResponses()) {
            if (surveyOptionRes.getSurveyOptionId().equals(surveyOption.getId())) {
                surveyOptionResultDtos.add(createSurveyOptionResult(surveyOption, surveyOptionRes));
                return;
            }
        }
        throw new IllegalArgumentException("설문 조사 항목 id : " + surveyOption.getId() + " 이 존재하지 않습니다.");
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
            throw new IllegalArgumentException("설문 조사 항목 id : " + surveyOptionRes.getSurveyOptionId() + " 이 존재하지 않습니다.");
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

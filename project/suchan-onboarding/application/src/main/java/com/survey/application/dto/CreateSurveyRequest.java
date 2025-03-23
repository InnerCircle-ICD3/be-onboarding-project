package com.survey.application.dto;

import com.survey.domain.Survey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSurveyRequest {

    private String title;
    private String description;
    private List<SurveyOptionDto> surveyOptionDtos = new ArrayList<>();

    public Survey create() {
        return new Survey(
                title,
                description,
                surveyOptionDtos.stream()
                        .map(SurveyOptionDto::create)
                        .toList()
        );
    }
}

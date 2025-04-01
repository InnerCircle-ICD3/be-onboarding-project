package com.survey.application.dto;

import com.survey.domain.Survey;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSurveyRequest {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotEmpty
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

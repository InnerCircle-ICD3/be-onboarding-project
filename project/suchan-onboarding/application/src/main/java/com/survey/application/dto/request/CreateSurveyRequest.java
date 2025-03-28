package com.survey.application.dto.request;

import com.survey.application.dto.dto.CreateSurveyOptionDto;
import com.survey.domain.Survey;
import jakarta.validation.Valid;
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
    @Valid
    private List<CreateSurveyOptionDto> surveyOptionDtos = new ArrayList<>();

    public Survey create() {
        return new Survey(
                title,
                description,
                surveyOptionDtos.stream()
                        .map(CreateSurveyOptionDto::create)
                        .toList()
        );
    }
}

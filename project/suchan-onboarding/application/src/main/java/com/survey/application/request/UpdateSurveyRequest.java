package com.survey.application.request;

import com.survey.application.dto.SurveyOptionDto;
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
public class UpdateSurveyRequest {

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

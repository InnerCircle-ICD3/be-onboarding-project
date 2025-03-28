package com.survey.application.dto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.survey.domain.SurveyOption;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSurveyOptionDto {
    private Long surveyOptionId;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    @JsonProperty("isNecessary")
    private Boolean isNecessary;

    @NotNull
    @Valid
    private UpdateInputFormDto inputFormDto;

    public SurveyOption create() {
        return new SurveyOption(
                surveyOptionId,
                title,
                description,
                isNecessary,
                inputFormDto.create()
        );
    }

}

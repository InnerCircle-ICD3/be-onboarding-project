package com.survey.application.dto.dto;

import com.survey.domain.survey.SurveyOption;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSurveyOptionDto {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private boolean isNecessary;

    @NotNull
    @Valid
    private CreateInputFormDto inputFormDto;

    public SurveyOption create() {
        return new SurveyOption(
                title,
                description,
                isNecessary,
                inputFormDto.create()
        );
    }

}

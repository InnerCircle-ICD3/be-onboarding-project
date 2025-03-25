package com.survey.application.dto;

import com.survey.domain.SurveyOption;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyOptionDto {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private boolean isNecessary;

    @NotNull
    private InputFormDto inputFormDto;

    public SurveyOption create() {
        return new SurveyOption(
                title,
                description,
                isNecessary,
                inputFormDto.create()
        );
    }

}

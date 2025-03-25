package com.survey.application.dto.dto;

import com.survey.domain.SurveyOption;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSurveyOptionDto {
    @NotNull
    private Long surveyOptionId;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private boolean isNecessary;

    @NotNull
    private UpdateInputFormDto inputFormDto;

    public SurveyOption create() {
        return new SurveyOption(
                title,
                description,
                isNecessary,
                inputFormDto.create()
        );
    }

}

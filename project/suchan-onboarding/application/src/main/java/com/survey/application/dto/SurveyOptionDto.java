package com.survey.application.dto;

import com.survey.domain.SurveyOption;
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
public class SurveyOptionDto {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private boolean isNecessary;

    @NotEmpty
    private List<InputFormDto> inputFormDtos = new ArrayList<>();

    public SurveyOption create() {
        return new SurveyOption(
                title,
                description,
                isNecessary,
                inputFormDtos.stream()
                        .map(InputFormDto::create)
                        .toList()
        );
    }
}

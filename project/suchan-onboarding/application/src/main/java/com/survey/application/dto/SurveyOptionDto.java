package com.survey.application.dto;

import com.survey.domain.SurveyOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyOptionDto {

    private String title;
    private String description;
    private boolean isNecessary;
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

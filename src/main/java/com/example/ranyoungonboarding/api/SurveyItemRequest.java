package com.example.ranyoungonboarding.api;

import com.example.ranyoungonboarding.domain.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyItemRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private QuestionType type;

    @NotNull
    private Boolean required;

    private List<String> options;
}
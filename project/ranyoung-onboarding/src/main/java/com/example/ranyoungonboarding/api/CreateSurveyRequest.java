package com.example.ranyoungonboarding.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateSurveyRequest {

    @NotBlank
    private String name;
    private String description;

    @NotEmpty
    @Size(min = 1, max = 10)
    private List<SurveyItemRequest> questions;
}
package com.innercircle.yeonwoo_onboarding.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyItemResponseCreateDto {
    private String surveyItemId;
    private String textResponse;
    private List<String> selectedOptionIds;  // For SINGLE/MULTIPLE choice questions
}
package com.innercircle.yeonwoo_onboarding.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyResponseCreateDto {
    private String surveyId;
    private String respondentName;
    private List<SurveyItemResponseCreateDto> responses;
}
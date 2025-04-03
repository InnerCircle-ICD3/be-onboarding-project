package com.innercircle.yeonwoo_onboarding.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SurveyResponseDto {
    private String id;
    private String surveyId;
    private String respondentName;
    private LocalDateTime submittedAt;
    private List<SurveyItemResponseDto> responses;
}
package com.innercircle.yeonwoo_onboarding.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SurveyCreateDto {
    private String name;
    private String description;
    private List<SurveyItemCreateDto> items;
}
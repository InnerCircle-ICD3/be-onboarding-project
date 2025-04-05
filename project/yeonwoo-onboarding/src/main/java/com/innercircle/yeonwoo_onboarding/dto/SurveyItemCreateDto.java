package com.innercircle.yeonwoo_onboarding.dto;

import com.innercircle.yeonwoo_onboarding.domain.enums.InputType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyItemCreateDto {
    private String name;
    private String description;
    private InputType inputType;
    private boolean required;
    private List<String> options; // InputType이 SINGLE/MULTIPLE인 경우에만 사용됨
}
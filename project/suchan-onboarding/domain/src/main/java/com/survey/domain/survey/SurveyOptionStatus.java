package com.survey.domain.survey;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SurveyOptionStatus {
    ACTIVE("정상 상태"),
    DELETED("삭제된 상태");

    private static final String SURVEY_OPTION_STATUS_EXCEPTION_PREFIX = "잘못된 설문 조사 항목 상태 변환 : ";

    private final String name;

    SurveyOptionStatus(String name) {
        this.name = name;
    }

    public static SurveyOptionStatus findByName(String status) {
        return Arrays.stream(SurveyOptionStatus.values())
                .filter(type -> type.getName().equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(SURVEY_OPTION_STATUS_EXCEPTION_PREFIX + status));
    }

}

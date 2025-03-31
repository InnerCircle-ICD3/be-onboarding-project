package com.innercircle.yeonwoo_onboarding.domain.enums;

public enum InputType {
    SHORT("01", "단답형"),
    LONG("02", "장문형"),
    SINGLE("03", "단일 선택 리스트"),
    MULTIPLE("04", "다중 선택 리스트");

    private final String code;
    private final String description;

    InputType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
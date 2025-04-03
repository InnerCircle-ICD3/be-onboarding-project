package com.survey.domain.survey;

import java.util.Arrays;

public enum ChoiceType {
    SINGLE("단일"), MULTIPLE("다중");

    private static final String CHOICE_TYPE_EXCEPTION_PREFIX = "잘못된 choice 타입 변환 : ";

    private final String name;

    ChoiceType(String name) {
        this.name = name;
    }

    public static ChoiceType findByName(String choiceType) {
        return Arrays.stream(ChoiceType.values())
                .filter(type -> type.getName().equals(choiceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(CHOICE_TYPE_EXCEPTION_PREFIX + choiceType));
    }

    private String getName() {
        return this.name;
    }
}

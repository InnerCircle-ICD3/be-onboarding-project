package com.survey.domain;

import java.util.Arrays;

public enum ChoiceType {
    SINGLE("단일"), MULTIPLE("다중");

    private final String name;

    ChoiceType(String name) {
        this.name = name;
    }

    public static ChoiceType findByName(String choiceType) {
        return Arrays.stream(ChoiceType.values())
                .filter(type -> type.getName().equals(choiceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 타입 변환 " + choiceType));
    }

    private String getName() {
        return this.name;
    }
}

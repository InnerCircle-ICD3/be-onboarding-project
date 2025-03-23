package com.survey.domain;

import java.util.Arrays;

public enum TextType {
    SHORT("단답형"), LONG("장문형");

    private final String name;

    TextType(String name) {
        this.name = name;
    }

    public static TextType findByName(String textType) {
        return Arrays.stream(TextType.values())
                .filter(type -> type.getName().equals(textType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 타입 변환 " + textType));
    }

    private String getName() {
        return this.name;
    }
}

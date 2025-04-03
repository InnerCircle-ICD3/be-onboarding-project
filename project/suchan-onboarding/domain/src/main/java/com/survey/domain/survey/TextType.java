package com.survey.domain.survey;

import java.util.Arrays;

public enum TextType {
    SHORT("단답형"), LONG("장문형");

    private static final String TEXT_TYPE_EXCEPTION_PREFIX = "잘못된 text 타입 변환 : ";

    private final String name;

    TextType(String name) {
        this.name = name;
    }

    public static TextType findByName(String textType) {
        return Arrays.stream(TextType.values())
                .filter(type -> type.getName().equals(textType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(TEXT_TYPE_EXCEPTION_PREFIX + textType));
    }

    public String getName() {
        return this.name;
    }
}

package me.dhlee.donghyeononboarding.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemType {

    SHORT_TEXT("단답형"),
    LONG_TEXT("장문형"),
    SINGLE_SELECT("단일선택 리스트"),
    MULTI_SELECT("다중선택 리스트"),
    ;

    private final String description;

    public boolean isSelectable() {
        return this == SINGLE_SELECT || this == MULTI_SELECT;
    }
}

package me.dhlee.donghyeononboarding.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SURVEY_TITLE_IS_EMPTY("설문조사 제목은 필수 입력사항입니다."),
    SURVEY_DESCRIPTION_IS_EMPTY("설문조사 설명은 필수 입력사항입니다."),

    SURVEY_ITEM_TITLE_IS_EMPTY("설문조사 항목 제목은 필수 입력사항입니다."),
    SURVEY_ITEM_DESCRIPTION_IS_EMPTY("설문조사 항목 설명은 필수 입력사항입니다."),
    SURVEY_ITEM_TYPE_IS_EMPTY("설문조사 항목 타입은 필수 입력사항입니다."),
    SURVEY_ITEM_IS_EMPTY("설문조사 항목은 필수 입력사항입니다."),
    SURVEY_ITEM_SIZE_OVERFLOW("설문조사 항목은 최대 10개까지 가능합니다."),

    SURVEY_ITEM_OPTION_TITLE_IS_EMPTY("설문조사 항목 옵션 제목은 필수 입력사항입니다."),
    SURVEY_ITEM_OPTION_IS_EMPTY("설문조사 항목 옵션은 필수 입력사항입니다."),
    SURVEY_ITEM_OPTION_SIZE_OVERFLOW("설문조사 항목 옵션은 최대 10개까지 가능합니다."),
    NOT_SELECTABLE_ITEM_TYPE_SHOULD_HAVE_NOT_OPTIONS("비선택형 항목은 설문조사 항목을 가질 수 없습니다."),

    DISPLAY_ORDER_IS_NEGATIVE("항목 표시순서는 0 이상이어야 합니다."),

    ;
    private final String message;
}

package me.dhlee.donghyeononboarding.dto.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.dhlee.donghyeononboarding.domain.ItemType;
import me.dhlee.donghyeononboarding.exception.AppException;
import me.dhlee.donghyeononboarding.exception.ErrorCode;

class SurveyItemCreateRequestTest {

    @DisplayName("설문조사 항목 제목이 비어있으면 예외가 발생한다.")
    @Test
    void createSurveyWithEmptyItemTitle() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);

        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("", "질문 설명1", ItemType.MULTI_SELECT, true, 0, List.of(option)))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_TITLE_IS_EMPTY.getMessage());
    }

    @DisplayName("설문조사 항목 설명이 비어있으면 예외가 발생한다.")
    @Test
    void createSurveyWithEmptyItemDescription() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);

        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("질문1", "", ItemType.MULTI_SELECT, true, 0, List.of(option)))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_DESCRIPTION_IS_EMPTY.getMessage());
    }

    @DisplayName("설문조사 항목 타입이 비어있으면 예외가 발생한다.")
    @Test
    void createSurveyWithNullItemType() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);

        assertThatThrownBy(() -> new SurveyItemCreateRequest("질문1", "질문 설명1", null, true, 0, List.of(option)))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_TYPE_IS_EMPTY.getMessage());
    }

    @DisplayName("설문조사 항목 표시순서가 음수이면 예외가 발생한다.")
    @Test
    void createSurveyWithNegativeItemDisplayOrder() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);

        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.MULTI_SELECT, true, -1, List.of(option)))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.DISPLAY_ORDER_IS_NEGATIVE.getMessage());
    }

    @DisplayName("선택형 항목(SINGLE_SELECT, MULTI_SELECT)이고 옵션이 비어있으면 예외가 발생한다.")
    @Test
    void createSurveyWithSelectableItemTypeAndEmptyOptions() {
        // SINGLE_SELECT 테스트
        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.SINGLE_SELECT, true, 0,
                Collections.emptyList()))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_OPTION_IS_EMPTY.getMessage());

        // MULTI_SELECT 테스트
        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.MULTI_SELECT, true, 0, Collections.emptyList()))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_OPTION_IS_EMPTY.getMessage());
    }

    @DisplayName("선택형 항목(SINGLE_SELECT, MULTI_SELECT)이고 옵션이 10개 이상이면 예외가 발생한다.")
    @Test
    void createSurveyWithSelectableItemTypeAndTooManyOptions() {
        var options = IntStream.rangeClosed(0, 10)
            .mapToObj(i -> new SurveyItemOptionCreateRequest("옵션" + i, i))
            .toList();

        // SINGLE_SELECT 테스트
        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.SINGLE_SELECT, true, 0, options))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_OPTION_SIZE_OVERFLOW.getMessage());

        // MULTI_SELECT 테스트
        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.MULTI_SELECT, true, 0, options))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_OPTION_SIZE_OVERFLOW.getMessage());
    }

    @DisplayName("비선택형 항목(SHORT_TEXT, LONG_TEXT)이고 옵션이 비어있지 않으면 예외가 발생한다.")
    @Test
    void createSurveyWithNonSelectableItemTypeAndNonEmptyOptions() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);

        // SHORT_TEXT 테스트
        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.SHORT_TEXT, true, 0, List.of(option)))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.NOT_SELECTABLE_ITEM_TYPE_SHOULD_HAVE_NOT_OPTIONS.getMessage());

        // LONG_TEXT 테스트
        assertThatThrownBy(
            () -> new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.LONG_TEXT, true, 0, List.of(option)))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.NOT_SELECTABLE_ITEM_TYPE_SHOULD_HAVE_NOT_OPTIONS.getMessage());
    }
}

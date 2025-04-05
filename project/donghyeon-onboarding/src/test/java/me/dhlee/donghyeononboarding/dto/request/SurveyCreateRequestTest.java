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

class SurveyCreateRequestTest {

    @DisplayName("설문조사 제목이 비어있으면 예외가 발생한다.")
    @Test
    void createSurveyWithEmptyTitle() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);
        var item = new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.MULTI_SELECT, true, 0, List.of(option));

        assertThatThrownBy(() -> new SurveyCreateRequest("", "설문 설명", List.of(item)))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_TITLE_IS_EMPTY.getMessage());
    }

    @DisplayName("설문조사 설명이 비어있으면 예외가 발생한다.")
    @Test
    void createSurveyWithEmptyDescription() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);
        var item = new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.MULTI_SELECT, true, 0, List.of(option));

        assertThatThrownBy(() -> new SurveyCreateRequest("설문 제목", "", List.of(item)))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_DESCRIPTION_IS_EMPTY.getMessage());
    }

    @DisplayName("설문조사 항목이 비어있으면 예외가 발생한다.")
    @Test
    void createSurveyWithEmptyItems() {
        assertThatThrownBy(() -> new SurveyCreateRequest("설문 제목", "설문 설명", Collections.emptyList()))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_IS_EMPTY.getMessage());
    }

    @DisplayName("설문조사 항목이 10개를 초과하면 예외가 발생한다.")
    @Test
    void createSurveyWithTooManyItems() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);
        var item = new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.MULTI_SELECT, true, 0, List.of(option));
        var items = IntStream.rangeClosed(0, 10)
            .mapToObj(i -> item)
            .toList();

        assertThatThrownBy(() -> new SurveyCreateRequest("설문 제목", "설문 설명", items))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_SIZE_OVERFLOW.getMessage());
    }
}
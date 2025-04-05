package me.dhlee.donghyeononboarding.dto.request;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.dhlee.donghyeononboarding.exception.AppException;
import me.dhlee.donghyeononboarding.exception.ErrorCode;

class SurveyItemOptionCreateRequestTest {

    @DisplayName("설문조사 항목 옵션 제목이 비어있으면 예외가 발생한다.")
    @Test
    void createSurveyWithEmptyOptionTitle() {
        assertThatThrownBy(() -> new SurveyItemOptionCreateRequest("", 0))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.SURVEY_ITEM_OPTION_TITLE_IS_EMPTY.getMessage());
    }

    @DisplayName("설문조사 항목 옵션 표시순서가 음수이면 예외가 발생한다.")
    @Test
    void createSurveyWithNegativeOptionDisplayOrder() {
        assertThatThrownBy(() -> new SurveyItemOptionCreateRequest("옵션1", -1))
            .isInstanceOf(AppException.class)
            .hasMessage(ErrorCode.DISPLAY_ORDER_IS_NEGATIVE.getMessage());
    }

}
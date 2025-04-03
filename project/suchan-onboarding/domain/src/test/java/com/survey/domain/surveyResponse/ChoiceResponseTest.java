package com.survey.domain.surveyResponse;

import com.survey.domain.survey.ChoiceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChoiceResponseTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("선택 응답 타입이 null 이거나 비어있으면 예외를 던진다.")
    void exception(List<String> selectedOptions) {
        // when // then
        assertThatThrownBy(() -> new ChoiceResponse(ChoiceType.SINGLE, selectedOptions))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택된 항목이 비어있습니다.");
    }

}
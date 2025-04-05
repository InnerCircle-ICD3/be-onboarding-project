package com.survey.domain.surveyResponse;

import com.survey.domain.survey.TextType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TextResponseTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("text 응답 값이 비어있으면 예외를 던진다.")
    void exception(String answer) {
        // when // then
        assertThatThrownBy(() -> new TextResponse(TextType.LONG, answer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("텍스트 응답 내용이 비어있습니다.");
    }

}
package com.survey.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChoiceInputFormTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("선택 항목 입력 형태의 항목이 비어있으면, 예외가 발생한다.")
    void create_exception(List<InputOption> given) {
        // when // then
        assertThatThrownBy(() -> new ChoiceInputForm(ChoiceType.SINGLE, given))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택 항목은 1개 이상이어야 합니다.");
    }

}
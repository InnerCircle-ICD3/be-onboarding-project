package com.survey.domain.survey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @ParameterizedTest
    @EnumSource(ChoiceType.class)
    @DisplayName("같은 폼인지 판단하고 맞다면 true를 반환한다.")
    void is_same_form_true(ChoiceType given) {
        // given
        ChoiceInputForm targetForm = new ChoiceInputForm(given, List.of(
                new InputOption("옵션1"),
                new InputOption("옵션2"),
                new InputOption("옵션3")
        ));

        ChoiceInputForm otherForm = new ChoiceInputForm(given, List.of(
                new InputOption("옵션1"),
                new InputOption("옵션2"),
                new InputOption("옵션3")
        ));

        // when
        boolean result = targetForm.isSameForm(otherForm);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @EnumSource(ChoiceType.class)
    @DisplayName("같은 폼인지 판단하고 틀리면 false 반환한다.")
    void is_same_form_false(ChoiceType given) {
        // given
        ChoiceInputForm targetForm = new ChoiceInputForm(given, List.of(
                new InputOption("옵션1"),
                new InputOption("옵션2"),
                new InputOption("옵션3")
        ));

        ChoiceInputForm otherForm = new ChoiceInputForm(given, List.of(
                new InputOption("옵션1"),
                new InputOption("옵션2"),
                new InputOption("옵션4")
        ));

        // when
        boolean result = targetForm.isSameForm(otherForm);

        // then
        assertThat(result).isFalse();
    }

}
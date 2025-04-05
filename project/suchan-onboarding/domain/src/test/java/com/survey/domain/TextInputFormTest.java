package com.survey.domain;

import com.survey.domain.survey.ChoiceType;
import com.survey.domain.survey.TextInputForm;
import com.survey.domain.survey.TextType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class TextInputFormTest {

    @ParameterizedTest
    @EnumSource(ChoiceType.class)
    @DisplayName("같은 폼인지 판단하고 맞다면 true를 반환한다.")
    void is_same_form_true() {
        // given
        TextInputForm targetForm = new TextInputForm(TextType.LONG);
        TextInputForm otherForm = new TextInputForm(TextType.LONG);

        // when
        boolean result = targetForm.isSameForm(otherForm);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "SHORT, LONG",
            "LONG, SHORT"
    })
    @DisplayName("같은 폼인지 판단하고 틀리면 false 반환한다.")
    void is_same_form_false(TextType given, TextType otherGiven) {
        // given
        TextInputForm targetForm = new TextInputForm(given);
        TextInputForm otherForm = new TextInputForm(otherGiven);

        // when
        boolean result = targetForm.isSameForm(otherForm);

        // then
        assertThat(result).isFalse();
    }

}
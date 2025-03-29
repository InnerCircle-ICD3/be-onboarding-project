package com.survey.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputFormTest {

    @Test
    @DisplayName("텍스트 입력 형태를 가지는 InputForm을 생성할 수 있다.")
    void create_with_text_input_form() {
        // given
        String question = "질문";
        TextInputForm textInputForm = new TextInputForm(TextType.LONG);

        // when
        InputForm inputForm = new InputForm(question, textInputForm);

        // then
        assertThat(inputForm.getQuestion()).isEqualTo(question);
        assertThat(inputForm.getTextInputForm()).isEqualTo(textInputForm);
        assertThat(textInputForm.getInputForm()).isEqualTo(inputForm);
    }

    @Test
    @DisplayName("선택 입력 형태를 가지는 InputForm을 생성할 수 있다.")
    void create_with_choice_input_form() {
        // given
        String question = "질문";
        ChoiceInputForm choiceInputForm = new ChoiceInputForm(ChoiceType.SINGLE, List.of(new InputOption("질문1")));

        // when
        InputForm inputForm = new InputForm(question, choiceInputForm);

        // then
        assertThat(inputForm.getQuestion()).isEqualTo(question);
        assertThat(inputForm.getChoiceInputForm()).isEqualTo(choiceInputForm);
        assertThat(choiceInputForm.getInputForm()).isEqualTo(inputForm);
    }

    @Test
    @DisplayName("두 개의 입력 형태를 가지는 InputForm을 생성하면 예외가 발생한다.")
    void create_multiple_type_exception() {
        // given
        TextInputForm textInputForm = new TextInputForm(TextType.LONG);
        ChoiceInputForm choiceInputForm = new ChoiceInputForm(ChoiceType.MULTIPLE, List.of(new InputOption("항목1"), new InputOption("항목2")));

        // when // then
        assertThatThrownBy(() -> new InputForm("질문1", textInputForm, choiceInputForm))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력 형태가 2가지일 수는 없습니다.");
    }

    @Test
    @DisplayName("0개의 타입으로 InputForm을 만들면 예외가 발생한다.")
    void create_non_type_exception() {
        // given
        String question = "질문1";

        // when // then
        assertThatThrownBy(() -> new InputForm(question))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 하나의 입력 형태는 존재해야 합니다.");
    }

}
package com.survey.domain;

import com.survey.test.TestSurveyEntityComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("isNeededModify 메소드 테스트")
    class IsNeededModifyTest {

        @Test
        @DisplayName("같은 TextInputForm 타입이지만 내용이 다른 경우, 수정이 필요하다")
        void different_text_input_form_content() {
            // given
            InputForm existingForm = new InputForm("원래 질문", new TextInputForm(TextType.SHORT));
            InputForm newForm = new InputForm("원래 질문", new TextInputForm(TextType.LONG));

            // when
            boolean needModify = existingForm.isNeededModify(newForm);

            // then
            assertThat(needModify).isTrue();
        }

        @Test
        @DisplayName("같은 TextInputForm 타입이고 내용도 같은 경우, 수정이 필요하지 않다")
        void same_text_input_form_content() {
            // given
            InputForm existingForm = new InputForm("원래 질문", new TextInputForm(TextType.SHORT));
            InputForm newForm = new InputForm("새 질문", new TextInputForm(TextType.SHORT));

            // when
            boolean needModify = existingForm.isNeededModify(newForm);

            // then
            assertThat(needModify).isFalse();
        }

        @Test
        @DisplayName("같은 ChoiceInputForm 타입이지만 내용이 다른 경우, 수정이 필요하다")
        void different_choice_input_form_content() {
            // given
            List<InputOption> existingOptions = List.of(new InputOption("옵션1"), new InputOption("옵션2"));
            List<InputOption> newOptions = List.of(new InputOption("옵션1"), new InputOption("변경된 옵션2"));

            InputForm original = new InputForm("원래 질문", new ChoiceInputForm(ChoiceType.SINGLE, existingOptions));
            InputForm newForm = new InputForm("원래 질문", new ChoiceInputForm(ChoiceType.SINGLE, newOptions));

            // when
            boolean needModify = original.isNeededModify(newForm);

            // then
            assertThat(needModify).isTrue();
        }

        @Test
        @DisplayName("같은 ChoiceInputForm 타입이고 내용도 같은 경우, 수정이 필요하지 않다")
        void same_choice_input_form_content() {
            // given
            List<InputOption> options = List.of(new InputOption("옵션1"), new InputOption("옵션2"));

            InputForm original = new InputForm("원래 질문", new ChoiceInputForm(ChoiceType.SINGLE, options));
            InputForm newForm = new InputForm("새 질문", new ChoiceInputForm(ChoiceType.SINGLE, List.of(new InputOption("옵션1"), new InputOption("옵션2"))));

            // when
            boolean needModify = original.isNeededModify(newForm);

            // then
            assertThat(needModify).isFalse();
        }

        @Test
        @DisplayName("TextInputForm에서 ChoiceInputForm으로 변경하는 경우, 수정이 필요하다")
        void change_from_text_to_choice() {
            // given
            InputForm original = new InputForm("원래 질문", new TextInputForm(TextType.SHORT));
            InputForm newForm = new InputForm("새 질문", new ChoiceInputForm(ChoiceType.SINGLE, List.of(new InputOption("옵션1"))));

            // when
            boolean needModify = original.isNeededModify(newForm);

            // then
            assertThat(needModify).isTrue();
        }

        @Test
        @DisplayName("ChoiceInputForm에서 TextInputForm으로 변경하는 경우, 수정이 필요하다")
        void change_from_choice_to_text() {
            // given
            InputForm existingForm = new InputForm("원래 질문", new ChoiceInputForm(ChoiceType.SINGLE, List.of(new InputOption("옵션1"))));
            InputForm newForm = new InputForm("새 질문", new TextInputForm(TextType.SHORT));

            // when
            boolean needModify = existingForm.isNeededModify(newForm);

            // then
            assertThat(needModify).isTrue();
        }
    }

    @Nested
    @DisplayName("modifyInputForm 메소드 테스트")
    class ModifyInputFormTest {

        private TestSurveyEntityComparator comparator;

        @BeforeEach
        void setUp() {
            comparator = new TestSurveyEntityComparator();
        }

        @Test
        @DisplayName("TextInputForm의 타입이 변경되면 수정된다")
        void update_text_input_form_type() {
            // given
            InputForm existingForm = new InputForm("원래 질문", new TextInputForm(TextType.SHORT));
            InputForm newForm = new InputForm("새 질문", new TextInputForm(TextType.LONG));

            // when
            existingForm.modifyInputForm(newForm);

            // then
            assertThat(comparator.assertValuesAreEqualInputForm(existingForm, newForm)).isTrue();
        }

        @Test
        @DisplayName("ChoiceInputForm의 타입과 옵션이 변경되면 수정된다")
        void update_choice_input_form_type_and_options() {
            // given
            List<InputOption> existingOptions = List.of(new InputOption("옵션1"), new InputOption("옵션2"));
            List<InputOption> newOptions = List.of(new InputOption("새 옵션1"), new InputOption("새 옵션2"), new InputOption("새 옵션3"));

            InputForm existingForm = new InputForm("원래 질문", new ChoiceInputForm(ChoiceType.SINGLE, existingOptions));
            InputForm newForm = new InputForm("새 질문", new ChoiceInputForm(ChoiceType.MULTIPLE, newOptions));

            // when
            existingForm.modifyInputForm(newForm);

            // then
            assertThat(comparator.assertValuesAreEqualInputForm(existingForm, newForm)).isTrue();
        }

        @Test
        @DisplayName("TextInputForm에서 ChoiceInputForm으로 변경된다")
        void replace_text_with_choice_input_form() {
            // given
            InputForm existingForm = new InputForm("원래 질문", new TextInputForm(TextType.SHORT));
            InputForm newForm = new InputForm("새 질문", new ChoiceInputForm(ChoiceType.SINGLE, List.of(new InputOption("옵션1"))));

            // when
            existingForm.modifyInputForm(newForm);

            // then
            assertThat(comparator.assertValuesAreEqualInputForm(existingForm, newForm)).isTrue();
        }

        @Test
        @DisplayName("ChoiceInputForm에서 TextInputForm으로 변경된다")
        void replace_choice_with_text_input_form() {
            // given
            InputForm existingForm = new InputForm("원래 질문", new ChoiceInputForm(ChoiceType.SINGLE, List.of(new InputOption("옵션1"))));
            InputForm newForm = new InputForm("새 질문", new TextInputForm(TextType.LONG));

            // when
            existingForm.modifyInputForm(newForm);

            // then
            assertThat(comparator.assertValuesAreEqualInputForm(existingForm, newForm)).isTrue();
        }
    }

}
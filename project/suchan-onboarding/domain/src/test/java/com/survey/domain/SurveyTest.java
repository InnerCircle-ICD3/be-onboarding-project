package com.survey.domain;

import com.survey.test.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SurveyTest {

    @Test
    @DisplayName("설문 조사를 만들면, 설문 받을 항목과 연관관계가 맺어진다.")
    void create() {
        // given
        SurveyOption givenOption = TestFixture.createSurveyOption();

        // when
        Survey result = new Survey("설문 조사 이름", "설명", List.of(givenOption));

        // then
        assertThat(result.getTitle()).isEqualTo("설문 조사 이름");
        assertThat(result.getSurveyOptions()).containsExactly(givenOption);
        assertThat(givenOption.getSurvey()).isEqualTo(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 11})
    @DisplayName("설문 조사의 설문 받을 항목이 1 미만이거나, 10 초과면 예외가 발생한다.")
    void validate_survey_options_count(int count) {
        // given
        SurveyOption givenOption = TestFixture.createSurveyOption();
        List<SurveyOption> surveyOptions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            surveyOptions.add(givenOption);
        }

        // when // then
        assertThatThrownBy(() -> new Survey("설문 조사 이름", "설명", surveyOptions))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("설문 받을 항목은 1개 ~ 10개 사이여야 합니다.");
    }

    @Test
    @DisplayName("설문조사를 수정할 수 있다.")
    void modify_survey() {
        // given
        Survey targetSurvey = TestFixture.createSurvey();
        Survey diffSurvey = TestFixture.createDiffSurvey();

        // when
        targetSurvey.modify(diffSurvey);

        // then
        assertThat(targetSurvey.getTitle()).isEqualTo("다른 설문 조사");
        assertThat(targetSurvey.getDescription()).isEqualTo("다른 설문 설명");
        assertThat(targetSurvey.getSurveyOptions().size()).isEqualTo(3);

        assertThat(targetSurvey.getSurveyOptions().get(0)).isEqualTo(diffSurvey.getSurveyOptions().get(0));
        assertThat(targetSurvey.getSurveyOptions().get(1)).isEqualTo(diffSurvey.getSurveyOptions().get(1));
        assertThat(targetSurvey.getSurveyOptions().get(2)).isEqualTo(diffSurvey.getSurveyOptions().get(2));
    }

    @Test
    @DisplayName("설문조사 수정 시 옵션이 null 또는 빈 리스트면 예외가 발생한다")
    void modify_survey_with_null_or_empty_options() {
        // given
        Survey targetSurvey = TestFixture.createSurvey();

        // when // then
        assertThatThrownBy(() -> targetSurvey.modify(new Survey("제목", "설명", null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("설문 받을 항목은 1개 ~ 10개 사이여야 합니다.");

        assertThatThrownBy(() -> targetSurvey.modify(new Survey("제목", "설명", new ArrayList<>())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("설문 받을 항목은 1개 ~ 10개 사이여야 합니다.");
    }

    @Test
    @DisplayName("설문조사 수정 시 기존 설문 받을 항목을 일부 유지하고 일부 제거할 수 있다")
    void modify_survey_remove_some_options() {
        // given
        Survey targetSurvey = TestFixture.createSurvey();

        List<SurveyOption> updatedOptions = List.of(
                TestFixture.createDiffSingleChoiceTypeSurveyOption(),
                TestFixture.createDiffLongTextTypeSurveyOption()
        );
        Survey updatedSurvey = new Survey("수정된 제목", "수정된 설명", updatedOptions);

        // when
        targetSurvey.modify(updatedSurvey);

        // then
        assertThat(targetSurvey.getTitle()).isEqualTo("수정된 제목");
        assertThat(targetSurvey.getDescription()).isEqualTo("수정된 설명");
        assertThat(targetSurvey.getSurveyOptions()).hasSize(2);

        ChoiceInputForm choiceInputForm = targetSurvey.getSurveyOptions().get(0).getInputForm().getChoiceInputForm();
        assertThat(choiceInputForm.getChoiceType()).isEqualTo(ChoiceType.SINGLE);

        TextInputForm textInputForm = targetSurvey.getSurveyOptions().get(1).getInputForm().getTextInputForm();
        assertThat(textInputForm.getTextType()).isEqualTo(TextType.LONG);
    }

    @Test
    @DisplayName("설문조사 수정 시 설문 받을 항목의 내용을 변경할 수 있다")
    void modify_survey_update_option_content() {
        // given
        Survey targetSurvey = TestFixture.createSurvey();
        SurveyOption originalOption = targetSurvey.getSurveyOptions().getFirst();
        Long originalOptionId = originalOption.getId();

        InputForm modifiedInputForm = new InputForm("수정된 질문", new TextInputForm(TextType.SHORT));
        SurveyOption modifiedOption = new SurveyOption(originalOptionId, "수정된 제목", "수정된 설명", true, modifiedInputForm);

        Survey updatedSurvey = new Survey("수정된 설문", "수정된 설명", List.of(modifiedOption));

        // when
        targetSurvey.modify(updatedSurvey);

        // then
        assertThat(targetSurvey.getTitle()).isEqualTo("수정된 설문");
        assertThat(targetSurvey.getSurveyOptions()).hasSize(1);

        SurveyOption resultOption = targetSurvey.getSurveyOptions().getFirst();
        assertThat(resultOption.getId()).isEqualTo(originalOptionId);
        assertThat(resultOption.getTitle()).isEqualTo("수정된 제목");
        assertThat(resultOption.getDescription()).isEqualTo("수정된 설명");
        assertThat(resultOption.getInputForm().getQuestion()).isEqualTo("수정된 질문");
    }

    @Test
    @DisplayName("설문조사 수정 시 새로운 설문 받을 항목을 추가하고 기존 설문 받을 항목을 제거할 수 있다")
    void modify_survey_add_new_and_remove_old_options() {
        // given
        Survey targetSurvey = TestFixture.createSurvey();

        // 완전히 새로운 옵션으로 구성된 설문
        SurveyOption newOption = TestFixture.createDiffLongTextTypeSurveyOption();
        Survey updatedSurvey = new Survey("수정된 제목", "수정된 설명", List.of(newOption));

        // when
        targetSurvey.modify(updatedSurvey);

        // then
        assertThat(targetSurvey.getTitle()).isEqualTo("수정된 제목");
        assertThat(targetSurvey.getSurveyOptions()).hasSize(1);
        assertThat(targetSurvey.getSurveyOptions().getFirst().getTitle()).isEqualTo("새로운 설문 받을 항목");
        assertThat(targetSurvey.getSurveyOptions().getFirst().getInputForm().getTextInputForm().getTextType()).isEqualTo(TextType.LONG);
    }

}
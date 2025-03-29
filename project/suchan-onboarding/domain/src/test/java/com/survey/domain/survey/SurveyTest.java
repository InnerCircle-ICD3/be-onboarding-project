package com.survey.domain.survey;

import com.survey.test.TestFixture;
import com.survey.test.TestSurveyEntityComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SurveyTest {

    private TestSurveyEntityComparator comparator;

    @BeforeEach
    void setUp() {
        this.comparator = new TestSurveyEntityComparator();
    }

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
        Survey existingSurvey = TestFixture.createSurvey();
        Survey newSurvey = TestFixture.createDiffSurvey();

        // when
        existingSurvey.modify(newSurvey);

        // then
        assertThat(comparator.assertValuesAreEqualSurvey(existingSurvey, newSurvey)).isTrue();
    }

    @Test
    @DisplayName("설문조사 수정 시 옵션이 null 또는 빈 리스트면 예외가 발생한다")
    void modify_survey_with_null_or_empty_options() {
        // given
        Survey existingSurvey = TestFixture.createSurvey();

        // when // then
        assertThatThrownBy(() -> existingSurvey.modify(new Survey("제목", "설명", null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("설문 받을 항목은 1개 ~ 10개 사이여야 합니다.");

        assertThatThrownBy(() -> existingSurvey.modify(new Survey("제목", "설명", new ArrayList<>())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("설문 받을 항목은 1개 ~ 10개 사이여야 합니다.");
    }

    @Test
    @DisplayName("설문조사 수정 시 기존 설문 받을 항목을 일부 유지하고 일부 제거할 수 있다")
    void modify_survey_remove_some_options() {
        // given
        Survey existingSurvey = TestFixture.createSurvey();

        List<SurveyOption> newOptions = List.of(
                TestFixture.createDiffSingleChoiceTypeSurveyOption(),
                TestFixture.createDiffLongTextTypeSurveyOption()
        );
        Survey updatedSurvey = new Survey("수정된 제목", "수정된 설명", newOptions);

        // when
        existingSurvey.modify(updatedSurvey);

        // then
        assertThat(existingSurvey.getTitle()).isEqualTo("수정된 제목");
        assertThat(existingSurvey.getDescription()).isEqualTo("수정된 설명");
        assertThat(existingSurvey.getSurveyOptions()).hasSize(2);

        ChoiceInputForm choiceInputForm = existingSurvey.getSurveyOptions().get(0).getInputForm().getChoiceInputForm();
        assertThat(choiceInputForm.getChoiceType()).isEqualTo(ChoiceType.SINGLE);

        TextInputForm textInputForm = existingSurvey.getSurveyOptions().get(1).getInputForm().getTextInputForm();
        assertThat(textInputForm.getTextType()).isEqualTo(TextType.LONG);
    }

    @Test
    @DisplayName("설문조사 수정 시 설문 받을 항목의 내용을 변경할 수 있다")
    void modify_survey_update_option_content() {
        // given
        Survey existingSurvey = TestFixture.createSurvey();
        SurveyOption existingOption = existingSurvey.getSurveyOptions().getFirst();
        Long existingOptionId = existingOption.getId();

        InputForm newInputForm = new InputForm("수정된 질문", new TextInputForm(TextType.SHORT));
        SurveyOption newOption = new SurveyOption(existingOptionId, "수정된 제목", "수정된 설명", true, newInputForm);

        Survey newSurvey = new Survey("수정된 설문", "수정된 설명", List.of(newOption));

        // when
        existingSurvey.modify(newSurvey);

        // then
        assertThat(existingSurvey.getTitle()).isEqualTo("수정된 설문");
        assertThat(existingSurvey.getSurveyOptions()).hasSize(1);

        SurveyOption resultOption = existingSurvey.getSurveyOptions().getFirst();
        assertThat(resultOption.getId()).isEqualTo(existingOptionId);
        assertThat(resultOption.getTitle()).isEqualTo("수정된 제목");
        assertThat(resultOption.getDescription()).isEqualTo("수정된 설명");
        assertThat(resultOption.getInputForm().getQuestion()).isEqualTo("수정된 질문");
    }

    @Test
    @DisplayName("설문조사 수정 시 새로운 설문 받을 항목을 추가하고 기존 설문 받을 항목을 제거할 수 있다")
    void modify_survey_add_new_and_remove_old_options() {
        // given
        Survey existingSurvey = TestFixture.createSurvey();

        SurveyOption newOption = TestFixture.createDiffLongTextTypeSurveyOption();
        Survey newSurvey = new Survey("수정된 제목", "수정된 설명", List.of(newOption));

        // when
        existingSurvey.modify(newSurvey);

        // then
        assertThat(existingSurvey.getTitle()).isEqualTo("수정된 제목");
        assertThat(existingSurvey.getSurveyOptions()).hasSize(1);
        assertThat(existingSurvey.getSurveyOptions().getFirst().getTitle()).isEqualTo("새로운 설문 받을 항목");
        assertThat(existingSurvey.getSurveyOptions().getFirst().getInputForm().getTextInputForm().getTextType()).isEqualTo(TextType.LONG);
    }

}
package com.survey.domain.survey;

import com.survey.test.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SurveyOptionTest {

    @Test
    @DisplayName("설문 받을 항목을 생성하면, 항목 입력 형태와 연관관계가 맺어진다.")
    void create() {
        // given
        InputForm given = new InputForm("질문1", new TextInputForm(TextType.LONG));

        // when
        SurveyOption result = new SurveyOption("설문 받을 항목", "설문 받을 항목 설명", true, given);

        // then
        assertThat(result.getTitle()).isEqualTo("설문 받을 항목");
        assertThat(result.getInputForm()).isEqualTo(given);
        assertThat(given.getSurveyOption()).isEqualTo(result);
    }

    @Test
    @DisplayName("설문 받을 항목을 다른 TEXT 타입의 항목으로 수정할 수 있다.")
    void modifyOption_to_diff_text() {
        // given
        SurveyOption existingSurveyOption = TestFixture.createSurveyOption();
        Long givenId = existingSurveyOption.getId();
        SurveyOption diffOption = TestFixture.createDiffLongTextTypeSurveyOption();

        // when
        existingSurveyOption.modifyOption(diffOption);

        // then
        assertThat(existingSurveyOption.getId()).isEqualTo(givenId);
        assertThat(existingSurveyOption.getDescription()).isEqualTo(diffOption.getDescription());
        assertThat(existingSurveyOption.getInputForm().getTextInputForm().getTextType())
                .isEqualTo(diffOption.getInputForm().getTextInputForm().getTextType());
    }

    @Test
    @DisplayName("설문 받을 항목을 다른 CHOICE 타입의 항목으로 수정할 수 있다.")
    void modifyOption_to_diff_choice() {
        // given
        SurveyOption existingSurveyOption = TestFixture.createSurveyOption();
        Long givenId = existingSurveyOption.getId();
        SurveyOption diffOption = TestFixture.createDiffSingleChoiceTypeSurveyOption();

        // when
        existingSurveyOption.modifyOption(diffOption);

        // then
        assertThat(existingSurveyOption.getId()).isEqualTo(givenId);
        assertThat(existingSurveyOption.getDescription()).isEqualTo(diffOption.getDescription());
        assertThat(existingSurveyOption.getInputForm().getChoiceInputForm().getChoiceType())
                .isEqualTo(diffOption.getInputForm().getChoiceInputForm().getChoiceType());

        int size = diffOption.getInputForm().getChoiceInputForm().getInputOptions().size();
        for (int i = 0; i < size; i++) {
            assertThat(existingSurveyOption.getInputForm().getChoiceInputForm().getInputOptions().get(i))
                    .isEqualTo(diffOption.getInputForm().getChoiceInputForm().getInputOptions().get(i));
        }
    }

}
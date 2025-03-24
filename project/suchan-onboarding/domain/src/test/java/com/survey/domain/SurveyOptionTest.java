package com.survey.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SurveyOptionTest {

    @Test
    @DisplayName("설문 받을 항목을 생성하면, 항목 입력 형태와 연관관계가 맺어진다.")
    void create() {
        // given
        InputForm given = new InputForm("질문1", new TextInputForm(TextType.LONG));

        // when
        SurveyOption result = new SurveyOption("설문 받을 항목", "설문 받을 항목 설명", true, List.of(given));

        // then
        assertThat(result.getTitle()).isEqualTo("설문 받을 항목");
        assertThat(result.getInputForms()).containsExactly(given);
        assertThat(given.getSurveyOption()).isEqualTo(result);
    }

}
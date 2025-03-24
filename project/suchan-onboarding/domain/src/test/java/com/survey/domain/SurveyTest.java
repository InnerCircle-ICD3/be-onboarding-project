package com.survey.domain;

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

}
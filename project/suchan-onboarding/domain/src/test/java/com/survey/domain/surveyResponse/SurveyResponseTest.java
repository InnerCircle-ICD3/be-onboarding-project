package com.survey.domain.surveyResponse;

import com.survey.domain.survey.TextType;
import jakarta.persistence.Column;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SurveyResponseTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("설문 응답 항목이 비어 있으면 예외를 던진다.")
    void validate_response_empty(List<SurveyOptionResponse> givenResponses) {
        // when // then
        assertThatThrownBy(() -> new SurveyResponse(1L, 1L, LocalDateTime.MAX, givenResponses))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("응답 항목이 비어있습니다.");
    }

    @Test
    @DisplayName("설문 조사 응답에 중복된 응답 항목이 있으면 예외를 던진다.")
    void validate_duplicate_response() {
        // given
        List<SurveyOptionResponse> duplicateResponses = new ArrayList<>();
        duplicateResponses.add(new SurveyOptionResponse(1L, new TextResponse(TextType.LONG, "응답입니다.")));
        duplicateResponses.add(new SurveyOptionResponse(1L, new TextResponse(TextType.LONG, "응답2입니다.")));

        // when // then
        assertThatThrownBy(() -> new SurveyResponse(1L, 1L, LocalDateTime.MAX, duplicateResponses))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 응답 항목에 대한 답변이 존재합니다.");
    }

    @ParameterizedTest
    @CsvSource({"1, true", "2, false", "0, false"})
    @DisplayName("설문지 버전과 설문 응답 버전이 같으면, true를 반환하고 다르면, false를 반환한다.")
    void is_same_version(Long givenVersion, boolean expected) {
        // given
        SurveyResponse surveyResponse = new SurveyResponse(
                1L,
                1L,
                LocalDateTime.MAX,
                List.of(new SurveyOptionResponse(
                        1L,
                        new TextResponse(TextType.LONG, "응답입니다.")
                )));

        // when // then
        assertThat(surveyResponse.isSameSurveyVersion(givenVersion)).isEqualTo(expected);
    }

}
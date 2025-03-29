package com.survey.domain.survey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChoiceTypeTest {

    @ParameterizedTest
    @MethodSource("provideChoiceType")
    @DisplayName("타입 이름으로 타입을 찾을 수 있다.")
    void find_by_name(String given, ChoiceType expected) {
        // given // when
        ChoiceType result = ChoiceType.findByName(given);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideChoiceType() {
        return Stream.of(
                Arguments.of("단일", ChoiceType.SINGLE),
                Arguments.of("다중", ChoiceType.MULTIPLE)
        );
    }

    @Test
    @DisplayName("존재하지 않는 타입 이름으로 찾으면 예외가 발생한다")
    void find_by_invalid_type_exception() {
        // given
        String given = "존재하지 않는 타입";

        // when // then
        assertThatThrownBy(() -> ChoiceType.findByName(given))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 choice 타입 변환 : " + given);
    }

}
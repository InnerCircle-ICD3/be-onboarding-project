package com.innercircle.survey.entity

import com.innercircle.domain.survey.command.dto.SurveyQuestionCreateCommand
import com.innercircle.domain.survey.command.dto.SurveyQuestionOptionCreateCommand
import com.innercircle.survey.entity.SurveyTestFixtures.survey
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SurveyQuestionTest {

    @CsvSource(
        "SHORT_ANSWER, Short answer question should not have options",
        "LONG_ANSWER, Long answer question should not have options"
    )
    @ParameterizedTest
    fun `서술형 질문은 옵션이 있으면 예외가 발생한다`(type: String, message: String) {
        // given
        val survey = survey()

        // when & then
        val assertThrows = assertThrows<IllegalStateException> {
            SurveyQuestion.of(
                survey, SurveyQuestionCreateCommand(
                    name = "첫사랑 이름은?",
                    description = "첫사랑 이름을 입력해주세요",
                    inputType = QuestionType.valueOf(type),
                    required = true,
                    options = listOf(
                        SurveyQuestionOptionCreateCommand(content = "옵션1"),
                        SurveyQuestionOptionCreateCommand(content = "옵션2")
                    )
                )
            )
        }

        assertThat(assertThrows.message).isEqualTo(message)
    }

    @CsvSource(
        "SINGLE_CHOICE, Single choice question should have options",
        "MULTI_CHOICE, Multi choice question should have options"
    )
    @ParameterizedTest
    fun `선택형 질문은 옵션이 없으면 예외가 발생한다`(type: String, message: String) {
        // given
        val survey = survey()

        // when & then
        val assertThrows = assertThrows<IllegalStateException> {
            SurveyQuestion.of(
                survey, SurveyQuestionCreateCommand(
                    name = "첫사랑 이름은?",
                    description = "첫사랑 이름을 입력해주세요",
                    inputType = QuestionType.valueOf(type),
                    required = true,
                    options = emptyList()
                )
            )
        }

        assertThat(assertThrows.message).isEqualTo(message)
    }

}
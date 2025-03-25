package com.example

import com.example.dto.AnswerDto
import com.example.dto.AnswerSubmitDto
import com.example.entity.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.UpdateSurveyService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class UpdateSurveyServiceTest {

    private val surveyRepository: SurveyRepository = mock()
    private val surveyAnswerRepository: SurveyAnswerRepository = mock()
    private val updateSurveyService = UpdateSurveyService(surveyRepository, surveyAnswerRepository)

    @Test
    fun `정상 응답 제출 시 저장 성공`() {
        val survey = Survey(
            id = 1L,
            title = "설문",
            description = "테스트"
        )
        val item = SurveyItem(
            id = 1L,
            name = "언어 선택",
            description = "좋아하는 언어",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(itemId = 1L, values = listOf("Kotlin"))
            )
        )

        assertDoesNotThrow {
            updateSurveyService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswer>>())
    }

    @Test
    fun `설문이 존재하지 않으면 예외 발생`() {
        whenever(surveyRepository.findById(999L)).thenReturn(java.util.Optional.empty())

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(itemId = 1L, values = listOf("Kotlin"))
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            updateSurveyService.submitAnswer(999L, request)
        }

        assertEquals("해당 설문이 존재하지 않습니다.", exception.message)
    }

    @Test
    fun `응답 항목이 설문 항목과 일치하지 않으면 예외 발생`() {
        val survey = Survey(
            id = 1L,
            title = "설문",
            description = "테스트"
        )
        val item = SurveyItem(
            id = 1L,
            name = "언어 선택",
            description = "좋아하는 언어",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(itemId = 999L, values = listOf("Kotlin"))
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            updateSurveyService.submitAnswer(1L, request)
        }

        assertEquals("응답 값이 설문 항목과 일치하지 않습니다.", exception.message)
    }

    @Test
    fun `입력 형태가 LONG_TEXT일 때 응답이 정상 저장된다`() {
        val survey = Survey(
            id = 1L,
            title = "자기소개 설문",
            description = "자기소개를 길게 작성해주세요"
        )
        val item = SurveyItem(
            id = 1L,
            name = "자기소개",
            description = "자세히 적어주세요",
            inputType = InputType.LONG_TEXT,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(itemId = 1L, values = listOf("저는 백엔드 개발자이며, 코틀린과 자바를 주로 사용하고 있어요!"))
            )
        )

        assertDoesNotThrow {
            updateSurveyService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswer>>())
    }

    @Test
    fun `SHORT_TEXT 응답이 너무 길면 예외 발생`() {
        val survey = Survey(
            id = 1L,
            title = "짧은 답변 테스트",
            description = "최대 길이 체크"
        )
        val item = SurveyItem(
            id = 1L,
            name = "한 줄 소개",
            description = "한 줄로 소개해주세요",
            inputType = InputType.SHORT_TEXT,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val longText = "a".repeat(300)

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(itemId = 1L, values = listOf(longText))
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            updateSurveyService.submitAnswer(1L, request)
        }

        assertEquals("SHORT_TEXT 응답은 255자 이내여야 합니다.", exception.message)
    }
}

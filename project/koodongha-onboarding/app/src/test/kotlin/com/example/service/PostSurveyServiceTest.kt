package com.example.service

import com.example.dto.SubmitAnswerRequest
import com.example.entity.InputType
import com.example.entity.Survey
import com.example.entity.SurveyItem
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class PostSurveyServiceTest {

    private val surveyRepository = mock<SurveyRepository>()
    private val surveyAnswerRepository = mock<SurveyAnswerRepository>()
    private val surveyAnswerService = SurveyAnswerService(surveyRepository, surveyAnswerRepository)

    @Test
    fun `정상 응답 제출 시 저장 성공`() {
        val survey = Survey(
            id = 1L,
            title = "설문",
            description = "테스트",
            items = listOf(
                SurveyItem(
                    id = 1L,
                    name = "언어 선택",
                    description = "좋아하는 언어",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Java", "Kotlin", "Python")
                )
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = SubmitAnswerRequest(
            answers = mapOf("1" to "Kotlin")
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).save(any())
    }

    @Test
    fun `설문이 존재하지 않으면 예외 발생`() {
        whenever(surveyRepository.findById(999L)).thenReturn(java.util.Optional.empty())

        val request = SubmitAnswerRequest(
            answers = mapOf("1" to "Kotlin")
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(999L, request)
        }

        assertEquals("해당 설문이 존재하지 않습니다.", exception.message)
    }

    @Test
    fun `응답 항목이 설문 항목과 일치하지 않으면 예외 발생`() {
        val survey = Survey(
            id = 1L,
            title = "설문",
            description = "테스트",
            items = listOf(
                SurveyItem(
                    id = 1L,
                    name = "언어 선택",
                    description = "좋아하는 언어",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Java", "Kotlin", "Python")
                )
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = SubmitAnswerRequest(
            answers = mapOf("999" to "Kotlin") // 존재하지 않는 항목
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("응답 값이 설문 항목과 일치하지 않습니다.", exception.message)
    }
}

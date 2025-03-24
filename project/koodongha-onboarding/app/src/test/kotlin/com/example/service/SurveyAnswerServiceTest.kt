package com.example.service

import com.example.dto.SubmitAnswerRequest
import com.example.entity.InputType
import com.example.entity.Survey
import com.example.entity.SurveyItem
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SurveyAnswerServiceTest {

    private val surveyRepository = mock<SurveyRepository>()
    private val surveyAnswerRepository = mock<SurveyAnswerRepository>()
    private val surveyAnswerService = SurveyAnswerService(surveyRepository, surveyAnswerRepository)

    @Test
    fun `정상 응답 제출 시 저장 성공`() {
        val survey = Survey(
            id = 1L,
            title = "응답 테스트 설문",
            description = "응답을 제출하고 조회하는 테스트",
            items = listOf(
                SurveyItem(1L, "언어 선택", "언어를 선택하세요", InputType.SINGLE_CHOICE, true, listOf("Kotlin", "Java")),
                SurveyItem(2L, "경력", "몇 년 하셨나요?", InputType.SHORT_TEXT, false)
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = SubmitAnswerRequest(
            answers = mapOf(
                "1" to "Kotlin",
                "2" to "3년"
            )
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).save(any())
    }

    @Test
    fun `응답 값이 설문 항목의 선택지와 일치하지 않으면 예외 발생`() {
        val survey = Survey(
            id = 1L,
            title = "응답 값 일치 테스트",
            description = "확인",
            items = listOf(
                SurveyItem(1L, "언어 선택", "언어를 선택하세요", InputType.SINGLE_CHOICE, true, listOf("Kotlin", "Java"))
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = SubmitAnswerRequest(
            answers = mapOf("1" to "Python")
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("선택형 항목에 적합한 응답 값을 입력해야 합니다.", exception.message)
    }

    @Test
    fun `입력 형태가 SHORT_TEXT인데 입력이 숫자나 리스트면 예외 발생`() {
        val survey = Survey(
            id = 1L,
            title = "입력 형태 테스트",
            description = "확인",
            items = listOf(
                SurveyItem(1L, "경력", "경력을 입력하세요", InputType.SHORT_TEXT, true)
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = SubmitAnswerRequest(
            answers = mapOf("1" to "[1,2,3]")
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).save(any())
    }

    @Test
    fun `존재하지 않는 설문일 경우 예외 발생`() {
        whenever(surveyRepository.findById(999L)).thenReturn(java.util.Optional.empty())

        val request = SubmitAnswerRequest(
            answers = mapOf("1" to "Kotlin")
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(999L, request)
        }

        assertEquals("해당 설문이 존재하지 않습니다.", exception.message)
    }
}
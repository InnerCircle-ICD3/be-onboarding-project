package com.example

import com.example.dto.AnswerDto
import com.example.dto.AnswerSubmitDto
import com.example.entity.InputType
import com.example.entity.SelectionOption
import com.example.entity.Survey
import com.example.entity.SurveyItem
import com.example.entity.SurveyAnswer
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.SurveyAnswerService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

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
            items = mutableListOf(
                SurveyItem(1L, "언어 선택", "언어를 선택하세요", InputType.SINGLE_CHOICE, true, survey = mock(), options = mutableListOf(
                    SelectionOption(value = "Kotlin", surveyItem = mock()),
                    SelectionOption(value = "Java", surveyItem = mock())
                )),
                SurveyItem(2L, "경력", "몇 년 하셨나요?", InputType.SHORT_TEXT, false, survey = mock())
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(1L, listOf("Kotlin")),
                AnswerDto(2L, listOf("3년"))
            )
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswer>>())
    }

    @Test
    fun `응답 값이 설문 항목의 선택지와 일치하지 않으면 예외 발생`() {
        val survey = Survey(
            id = 1L,
            title = "응답 값 일치 테스트",
            description = "확인",
            items = mutableListOf(
                SurveyItem(1L, "언어 선택", "언어를 선택하세요", InputType.SINGLE_CHOICE, true, survey = mock(), options = mutableListOf(
                    SelectionOption(value = "Kotlin", surveyItem = mock()),
                    SelectionOption(value = "Java", surveyItem = mock())
                ))
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(AnswerDto(1L, listOf("Python")))
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("선택형 항목에 적합한 응답 값을 입력해야 합니다.", exception.message)
    }

    @Test
    fun `존재하지 않는 설문일 경우 예외 발생`() {
        whenever(surveyRepository.findById(999L)).thenReturn(java.util.Optional.empty())

        val request = AnswerSubmitDto(
            answers = listOf(AnswerDto(1L, listOf("Kotlin")))
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(999L, request)
        }

        assertEquals("해당 설문이 존재하지 않습니다.", exception.message)
    }

    @Test
    fun `다중 선택 응답이 정상적으로 저장`() {
        val survey = Survey(
            id = 1L,
            title = "프로그래밍 언어 설문",
            description = "사용하는 언어를 모두 선택하세요",
            items = mutableListOf(
                SurveyItem(1L, "언어 선택", "사용하는 언어들", InputType.MULTI_CHOICE, true, survey = mock(), options = mutableListOf(
                    SelectionOption(value = "Kotlin", surveyItem = mock()),
                    SelectionOption(value = "Java", surveyItem = mock()),
                    SelectionOption(value = "Python", surveyItem = mock()),
                    SelectionOption(value = "Go", surveyItem = mock())
                ))
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(AnswerDto(1L, listOf("Kotlin", "Java")))
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswer>>())
    }

    @Test
    fun `다중 선택 응답 중 하나라도 옵션에 없으면 예외 발생`() {
        val survey = Survey(
            id = 1L,
            title = "언어 선택 설문",
            description = "다중 선택 테스트",
            items = mutableListOf(
                SurveyItem(1L, "언어", "사용 언어", InputType.MULTI_CHOICE, true, survey = mock(), options = mutableListOf(
                    SelectionOption(value = "Kotlin", surveyItem = mock()),
                    SelectionOption(value = "Java", surveyItem = mock()),
                    SelectionOption(value = "Python", surveyItem = mock())
                ))
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(AnswerDto(1L, listOf("Kotlin", "Scala")))
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("선택형 항목에 적합한 응답 값을 입력해야 합니다.", exception.message)
    }
}

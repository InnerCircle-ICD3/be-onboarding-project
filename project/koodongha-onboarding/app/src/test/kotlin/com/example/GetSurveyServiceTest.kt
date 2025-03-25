package com.example

import com.example.dto.SurveyResponse
import com.example.entity.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.GetSurveyService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.*

class GetSurveyServiceTest {

    private val surveyRepository: SurveyRepository = mock()
    private val answerRepository: SurveyAnswerRepository = mock()
    private val service = GetSurveyService(surveyRepository, answerRepository)

    @Test
    fun `존재하는 설문을 정상적으로 조회할 수 있다`() {
        val surveyId = 1L
        val survey = Survey(surveyId, "테스트 설문", "설문 설명")
        val item = SurveyItem(
            name = "언어 선택",
            description = "언어를 골라주세요",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = true,
            survey = survey
        )
        val option = SelectionOption(value = "Kotlin", surveyItem = item)
        item.options.add(option)
        survey.items.add(item)

        val answer = SurveyAnswer(
            survey = survey,
            surveyItem = item,
            shortAnswer = "Kotlin"
        )

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(answer))

        val result: SurveyResponse = service.getSurvey(surveyId)

        assertEquals("테스트 설문", result.title)
        assertEquals(1, result.items.size)
        assertEquals("언어 선택", result.items[0].name)
        assertEquals("Kotlin", result.items[0].answers?.first())
    }

    @Test
    fun `존재하지 않는 설문 조회 시 예외 발생`() {
        whenever(surveyRepository.findById(9999L)).thenReturn(Optional.empty())

        val exception = assertThrows(RuntimeException::class.java) {
            service.getSurvey(9999L)
        }

        assertEquals("설문이 존재하지 않습니다.", exception.message)
    }

    @Test
    fun `설문 항목에 응답이 없을 경우 응답 리스트는 비어있다`() {
        val surveyId = 2L
        val survey = Survey(surveyId, "빈 응답 설문", "응답 없음")
        val item = SurveyItem(
            name = "취미",
            description = "취미를 입력하세요",
            inputType = InputType.SHORT_TEXT,
            isRequired = false,
            survey = survey
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(emptyList())

        val result = service.getSurvey(surveyId)

        assertEquals("빈 응답 설문", result.title)
        assertEquals(1, result.items.size)
        assertTrue(result.items[0].answers?.isEmpty() == true)
    }

    @Test
    fun `답변 필터링 - 특정 항목 이름과 응답 값이 일치하는 경우`() {
        val surveyId = 3L
        val survey = Survey(surveyId, "필터 테스트", "특정 항목 필터")
        val item = SurveyItem(
            name = "프레임워크",
            description = "사용 프레임워크",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        val answer1 = SurveyAnswer(survey = survey, surveyItem = item, shortAnswer = "Spring")
        val answer2 = SurveyAnswer(survey = survey, surveyItem = item, shortAnswer = "Django")

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(answer1, answer2))

        val result = service.getSurvey(surveyId, filterName = "프레임워크", filterAnswer = "Spring")

        assertEquals(1, result.items.size)
        assertEquals("프레임워크", result.items[0].name)
        assertTrue(result.items[0].answers!!.contains("Spring"))
        assertFalse(result.items[0].answers!!.contains("Django"))
    }

    @Test
    fun `답변 필터링 - 필터 조건 불일치시 항목 제외`() {
        val surveyId = 4L
        val survey = Survey(surveyId, "필터 제외 테스트", "조건 불일치")
        val item = SurveyItem(
            name = "OS",
            description = "운영체제",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = false,
            survey = survey
        )
        survey.items.add(item)

        val answer = SurveyAnswer(survey = survey, surveyItem = item, shortAnswer = "Linux")

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(answer))

        val result = service.getSurvey(surveyId, filterName = "OS", filterAnswer = "Windows")

        assertTrue(result.items.isEmpty())
    }
}

package com.example

import com.example.entity.InputType
import com.example.entity.Survey
import com.example.entity.SurveyItem
import com.example.repository.SurveyRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class GetSurveyServiceTest @Autowired constructor(
    val surveyRepository: SurveyRepository
) {

    @Test
    fun `저장된 설문을 정상적으로 조회할 수 있다`() {
        val survey = Survey(
            title = "조회 테스트",
            description = "설명입니다."
        )
        val item = SurveyItem(
            name = "질문 1",
            description = "내용",
            inputType = InputType.SHORT_TEXT,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)
        val saved = surveyRepository.save(survey)

        val service = GetSurveyService(surveyRepository)

        val result = service.getSurvey(saved.id)

        assertEquals("조회 테스트", result.title)
        assertEquals(1, result.items.size)
        assertEquals("질문 1", result.items[0].name)
    }

    @Test
    fun `존재하지 않는 설문 조회 시 예외가 발생한다`() {
        val service = GetSurveyService(surveyRepository)

        val exception = assertThrows(RuntimeException::class.java) {
            service.getSurvey(9999L)
        }

        assertEquals("설문이 존재하지 않습니다.", exception.message)
    }

    @Test
    fun `설문 응답 조회 시 응답 항목과 값을 제대로 가져올 수 있다`() {
        val survey = Survey(
            title = "응답 테스트 설문",
            description = "응답을 제출하고 조회하는 테스트"
        )
        val item = SurveyItem(
            name = "언어 선택",
            description = "언어를 고르세요",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        val savedSurvey = surveyRepository.save(survey)

        val answer = Answer(
            surveyItem = item,
            answer = "Kotlin",
            survey = savedSurvey
        )
        val answerRepository = mock<AnswerRepository>()
        answerRepository.save(answer)

        val service = GetSurveyService(surveyRepository)

        val result = service.getSurvey(savedSurvey.id)

        assertEquals("응답 테스트 설문", result.title)
        assertEquals(1, result.items.size)
        assertEquals("언어 선택", result.items[0].name)
        assertEquals("Kotlin", result.items[0].options?.first())
    }

    @Test
    fun `옵션이 없는 설문 항목에 응답을 제출하려고 할 때 예외가 발생한다`() {
        val survey = Survey(
            title = "옵션 없는 항목 테스트",
            description = "옵션이 없는 선택형 항목에 응답을 제출하려고 할 때"
        )
        val item = SurveyItem(
            name = "언어 선택",
            description = "언어를 고르세요",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        val savedSurvey = surveyRepository.save(survey)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            Answer(
                surveyItem = item,
                answer = "Kotlin",
                survey = savedSurvey
            )
        }

        assertEquals("선택형 항목에는 옵션이 필수입니다.", exception.message)
    }

    @Test
    fun `설문 응답 항목 이름 및 응답 값으로 조회하기`() {
        val survey = Survey(
            title = "응답 항목 이름으로 조회",
            description = "응답 항목 이름과 응답 값으로 설문을 조회합니다."
        )
        val item = SurveyItem(
            name = "언어 선택",
            description = "언어를 고르세요",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        val savedSurvey = surveyRepository.save(survey)

        val answer = Answer(
            surveyItem = item,
            answer = "Kotlin",
            survey = savedSurvey
        )
        val answerRepository = mock<AnswerRepository>()
        answerRepository.save(answer)

        val service = GetSurveyService(surveyRepository)

        val result = service.getSurvey(savedSurvey.id)

        assertEquals("언어 선택", result.items[0].name)
        assertEquals("Kotlin", result.items[0].options?.first())
    }
}

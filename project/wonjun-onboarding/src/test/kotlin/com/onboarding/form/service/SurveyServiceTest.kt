package com.onboarding.form.service

import com.onboarding.form.domain.MultiSelectQuestion
import com.onboarding.form.domain.QuestionType
import com.onboarding.form.domain.SingleSelectQuestion
import com.onboarding.form.request.CreateSelectQuestionDto
import com.onboarding.form.request.CreateStandardQuestionDto
import com.onboarding.form.request.CreateSurveyDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class SurveyServiceTest {
    @Autowired
    lateinit var surveyService: SurveyService

    @Test
    @DisplayName("네가지 질문 타입에 맞춰서 설문조사를 생성할 수 있다.")
    fun createSurveyTest() {
        val createSurveyDto = CreateSurveyDto(
            title = "testTitle",
            description = "testDescription",
            questions = listOf(
                CreateSelectQuestionDto(
                    title = "testTitle",
                    description = "testDescription",
                    type = QuestionType.MULTI_SELECT,
                    isRequired = true,
                    answerList = listOf("testAnswer1", "testAnswer2")
                ),
                CreateSelectQuestionDto(
                    title = "testTitle",
                    description = "testDescription",
                    type = QuestionType.SINGLE_SELECT,
                    isRequired = true,
                    answerList = listOf("testAnswer1", "testAnswer2")
                ),
                CreateStandardQuestionDto(
                    title = "testTitle",
                    description = "testDescription",
                    type = QuestionType.SHORT,
                    isRequired = true,
                ),
                CreateStandardQuestionDto(
                    title = "testTitle",
                    description = "testDescription",
                    type = QuestionType.LONG,
                    isRequired = true,
                )
            )
        )

        val actualSurvey = surveyService.createSurvey(createSurveyDto)

        assertEquals(actualSurvey.title, createSurveyDto.title)
        assertEquals(actualSurvey.description, createSurveyDto.description)

        (actualSurvey.questions.zip(createSurveyDto.questions)).forEach {
            assertEquals(it.first.title, it.second.title)
            assertEquals(it.first.description, it.second.description)
            assertEquals(it.first.getType(), it.second.type)
            assertEquals(it.first.isRequired, it.second.isRequired)

            if (it.first is MultiSelectQuestion) {
                val selectQuestion = it.first as MultiSelectQuestion
                assertTrue(it.second is CreateSelectQuestionDto)
                val selectQuestionDto = it.second as CreateSelectQuestionDto
                selectQuestion.answerList.zip(selectQuestionDto.answerList).forEach {
                    assertEquals(it.first, it.second)
                }
            }

            if (it.first is SingleSelectQuestion) {
                val selectQuestion = it.first as SingleSelectQuestion
                assertTrue(it.second is CreateSelectQuestionDto)
                val selectQuestionDto = it.second as CreateSelectQuestionDto
                selectQuestion.answerList.zip(selectQuestionDto.answerList).forEach {
                    assertEquals(it.first, it.second)
                }
            }
        }
    }
}
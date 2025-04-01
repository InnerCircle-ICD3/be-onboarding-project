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
import org.junit.jupiter.api.assertThrows
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

        (actualSurvey.getQuestions().zip(createSurveyDto.questions)).forEach {
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
    @Test
    @DisplayName("Survey가 존재하지 않는데 수정을 시도할 시 에러가 발생한다.")
    fun updateSurveyTestSurveyIsNotExist() {
        val updateSurveyDto = CreateSurveyDto(
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
            )
        )


        assertThrows<IllegalArgumentException>{
            surveyService.updateSurveyDto(1, updateSurveyDto)
        }
    }

    @Test
    @DisplayName("네가지 질문 타입에 맞춰서 설문조사를 수정할 수 있다.")
    fun updateSurveyTest() {
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

        val updateSurveyDto = CreateSurveyDto(
            title = "testTitle2",
            description = "testDescription2",
            questions = listOf(
                CreateSelectQuestionDto(
                    title = "testTitle2",
                    description = "testDescription2",
                    type = QuestionType.MULTI_SELECT,
                    isRequired = true,
                    answerList = listOf("testAnswer12", "testAnswer22")
                ),
                CreateSelectQuestionDto(
                    title = "testTitle2",
                    description = "testDescription2",
                    type = QuestionType.SINGLE_SELECT,
                    isRequired = true,
                    answerList = listOf("testAnswer12", "testAnswer22")
                ),
                CreateStandardQuestionDto(
                    title = "testTitle2",
                    description = "testDescription2",
                    type = QuestionType.SHORT,
                    isRequired = true,
                ),
                CreateStandardQuestionDto(
                    title = "testTitle2",
                    description = "testDescription2",
                    type = QuestionType.LONG,
                    isRequired = true,
                )
            )
        )

        val updateActualSurvey = surveyService.updateSurveyDto(actualSurvey.id!!, updateSurveyDto)

        assertEquals(updateActualSurvey.title, updateSurveyDto.title)
        assertEquals(updateActualSurvey.description, updateSurveyDto.description)

        (updateActualSurvey.getQuestions().zip(updateSurveyDto.questions)).forEach {
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
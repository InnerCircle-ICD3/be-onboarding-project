package com.example

import com.example.dto.*
import com.example.entity.*
import com.example.exception.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.SurveyAnswerService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.Optional

class SurveyAnswerServiceTest {

    private val surveyRepository = mock<SurveyRepository>()
    private val surveyAnswerRepository = mock<SurveyAnswerRepository>()
    private val surveyAnswerService = SurveyAnswerService(surveyRepository, surveyAnswerRepository)

    @Test
    @DisplayName("Should save successfully when submitting valid answers")
    fun shouldSaveSuccessfullyWhenSubmittingValidAnswers() {
        val survey = Survey(
            id = 1L,
            title = "Answer Submission Test Survey",
            description = "Test for submitting and retrieving responses"
        )

        val singleChoiceItem = ChoiceItem(
            name = "Language Choice",
            description = "Choose a language",
            isRequired = true,
            isMultiple = false,
            survey = survey
        ).apply { id = 1L }

        val shortTextItem = TextItem(
            name = "Experience",
            description = "Years of experience",
            isRequired = false,
            survey = survey
        ).apply { id = 2L }

        val kotlinOption = SelectionOption(id = 10L, value = "Kotlin", item = singleChoiceItem)
        val javaOption = SelectionOption(value = "Java", item = singleChoiceItem)
        singleChoiceItem.options.addAll(listOf(kotlinOption, javaOption))

        survey.items.addAll(listOf(singleChoiceItem, shortTextItem))

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = singleChoiceItem.id, selectedOptionIds = listOf(kotlinOption.id)),
                TextAnswerDto(itemId = shortTextItem.id, value = "3 years")
            )
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswerBase>>())
    }

    @Test
    @DisplayName("Should throw exception when answer value does not match survey item options")
    fun shouldThrowExceptionWhenAnswerValueDoesNotMatchSurveyItemOptions() {
        val survey = Survey(
            id = 1L,
            title = "Answer Value Match Test",
            description = "Test if the answer matches the options"
        )

        val item = ChoiceItem(
            name = "Language Choice",
            description = "Choose a language",
            isRequired = true,
            isMultiple = false,
            survey = survey
        )

        val kotlin = SelectionOption(value = "Kotlin", item = item)
        val java = SelectionOption(value = "Java", item = item)
        item.options.addAll(listOf(kotlin, java))
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = item.id, selectedOptionIds = listOf(999L))
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("You must enter a valid answer for the selected options.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when survey does not exist")
    fun shouldThrowExceptionWhenSurveyDoesNotExist() {
        whenever(surveyRepository.findById(999L)).thenReturn(Optional.empty())

        val request = AnswerSubmitDto(
            answers = listOf(TextAnswerDto(itemId = 1L, value = "Kotlin"))
        )

        val exception = assertThrows(SurveyNotFoundException::class.java) {
            surveyAnswerService.submitAnswer(999L, request)
        }

        assertEquals(ErrorCode.SURVEY_NOT_FOUND.message, exception.message)
    }

    @Test
    @DisplayName("Should save multiple choice answers correctly")
    fun shouldSaveMultipleChoiceAnswersCorrectly() {
        val survey = Survey(
            id = 1L,
            title = "Programming Language Survey",
            description = "Select all the languages you use"
        )

        val item = ChoiceItem(
            name = "Language Choice",
            description = "Select all the languages you use",
            isRequired = true,
            isMultiple = true,
            survey = survey
        )

        val kotlin = SelectionOption(id = 100L, value = "Kotlin", item = item)
        val java = SelectionOption(id = 101L, value = "Java", item = item)
        item.options.addAll(listOf(kotlin, java))
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = item.id, selectedOptionIds = listOf(kotlin.id, java.id))
            )
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswerBase>>())
    }

    @Test
    @DisplayName("Should throw exception when one or more choices are not valid options")
    fun shouldThrowExceptionWhenChoiceIsNotValid() {
        val survey = Survey(
            id = 1L,
            title = "Language Selection Survey",
            description = "Multiple choice test"
        )

        val item = ChoiceItem(
            name = "Language",
            description = "Languages used",
            isRequired = true,
            isMultiple = true,
            survey = survey
        )

        val kotlin = SelectionOption(id = 200L, value = "Kotlin", item = item)
        val java = SelectionOption(id = 201L, value = "Java", item = item)
        val python = SelectionOption(id = 202L, value = "Python", item = item)
        item.options.addAll(listOf(kotlin, java, python))
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = item.id, selectedOptionIds = listOf(kotlin.id, 999L))
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("You must enter a valid answer for the selected options.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when required question is missing in answers")
    fun shouldThrowExceptionWhenRequiredQuestionIsMissing() {
        val survey = Survey(
            id = 1L,
            title = "Required Field Survey",
            description = "Test for required field"
        )

        val requiredTextItem = TextItem(
            name = "Your Name",
            description = "Please enter your name",
            isRequired = true,
            isLong = false,
            survey = survey
        ).apply { id = 1L }

        val optionalItem = TextItem(
            name = "Your Hobby",
            description = "Optional hobby",
            isRequired = false,
            isLong = false,
            survey = survey
        ).apply { id = 2L }

        survey.items.addAll(listOf(requiredTextItem, optionalItem))

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                TextAnswerDto(itemId = optionalItem.id, value = "Coding")
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("Required questions must be answered.", exception.message)
    }
}

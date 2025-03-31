package com.example

import com.example.dto.AnswerDto
import com.example.dto.AnswerSubmitDto
import com.example.entity.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.UpdateSurveyService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class UpdateSurveyServiceTest {

    private val surveyRepository: SurveyRepository = mock()
    private val surveyAnswerRepository: SurveyAnswerRepository = mock()
    private val updateSurveyService = UpdateSurveyService(surveyRepository, surveyAnswerRepository)

    @Test
    @DisplayName("Should save successfully when submitting valid answers")
    fun shouldSaveSuccessfullyWhenSubmittingValidAnswers() {
        val survey = Survey(
            id = 1L,
            title = "Survey",
            description = "Test"
        )
        val item = SurveyItem(
            id = 1L,
            name = "Language Choice",
            description = "Favorite Language",
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
    @DisplayName("Should throw exception when survey does not exist")
    fun shouldThrowExceptionWhenSurveyDoesNotExist() {
        whenever(surveyRepository.findById(999L)).thenReturn(java.util.Optional.empty())

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(itemId = 1L, values = listOf("Kotlin"))
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            updateSurveyService.submitAnswer(999L, request)
        }

        assertEquals("Survey not found.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when answer item does not match survey item")
    fun shouldThrowExceptionWhenAnswerItemDoesNotMatchSurveyItem() {
        val survey = Survey(
            id = 1L,
            title = "Survey",
            description = "Test"
        )
        val item = SurveyItem(
            id = 1L,
            name = "Language Choice",
            description = "Favorite Language",
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

        assertEquals("Answer value does not match survey item.", exception.message)
    }

    @Test
    @DisplayName("Should save successfully when input type is LONG_TEXT")
    fun shouldSaveSuccessfullyWhenInputTypeIsLongText() {
        val survey = Survey(
            id = 1L,
            title = "Self-Introduction Survey",
            description = "Please write a detailed self-introduction"
        )
        val item = SurveyItem(
            id = 1L,
            name = "Self-Introduction",
            description = "Please explain in detail",
            inputType = InputType.LONG_TEXT,
            isRequired = true,
            survey = survey
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(itemId = 1L, values = listOf("I am a backend developer, primarily using Kotlin and Java!"))
            )
        )

        assertDoesNotThrow {
            updateSurveyService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswer>>())
    }

    @Test
    @DisplayName("Should throw exception when SHORT_TEXT answer is too long")
    fun shouldThrowExceptionWhenShortTextAnswerIsTooLong() {
        val survey = Survey(
            id = 1L,
            title = "Short Answer Test",
            description = "Check for maximum length"
        )
        val item = SurveyItem(
            id = 1L,
            name = "One Line Introduction",
            description = "Please introduce yourself in one line",
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

        assertEquals("SHORT_TEXT answers must be within 255 characters.", exception.message)
    }
}

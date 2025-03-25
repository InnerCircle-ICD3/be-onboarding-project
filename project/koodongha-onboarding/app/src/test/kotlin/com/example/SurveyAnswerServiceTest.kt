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
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

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
            description = "Test for submitting and retrieving responses",
            items = mutableListOf(
                SurveyItem(1L, "Language Choice", "Choose a language", InputType.SINGLE_CHOICE, true, survey = mock(), options = mutableListOf(
                    SelectionOption(value = "Kotlin", surveyItem = mock()),
                    SelectionOption(value = "Java", surveyItem = mock())
                )),
                SurveyItem(2L, "Experience", "How many years have you been doing it?", InputType.SHORT_TEXT, false, survey = mock())
            )
        )

        whenever(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(1L, listOf("Kotlin")),
                AnswerDto(2L, listOf("3 years"))
            )
        )

        assertDoesNotThrow {
            surveyAnswerService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(any<List<SurveyAnswer>>())
    }

    @Test
    @DisplayName("Should throw exception when answer value does not match survey item options")
    fun shouldThrowExceptionWhenAnswerValueDoesNotMatchSurveyItemOptions() {
        val survey = Survey(
            id = 1L,
            title = "Answer Value Match Test",
            description = "Test if the answer matches the options",
            items = mutableListOf(
                SurveyItem(1L, "Language Choice", "Choose a language", InputType.SINGLE_CHOICE, true, survey = mock(), options = mutableListOf(
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

        assertEquals("You must enter a valid answer for the selected options.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when survey does not exist")
    fun shouldThrowExceptionWhenSurveyDoesNotExist() {
        whenever(surveyRepository.findById(999L)).thenReturn(java.util.Optional.empty())

        val request = AnswerSubmitDto(
            answers = listOf(AnswerDto(1L, listOf("Kotlin")))
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(999L, request)
        }

        assertEquals("Survey not found.", exception.message)
    }

    @Test
    @DisplayName("Should save multiple choice answers correctly")
    fun shouldSaveMultipleChoiceAnswersCorrectly() {
        val survey = Survey(
            id = 1L,
            title = "Programming Language Survey",
            description = "Select all the languages you use",
            items = mutableListOf(
                SurveyItem(1L, "Language Choice", "Select all the languages you use", InputType.MULTI_CHOICE, true, survey = mock(), options = mutableListOf(
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
    @DisplayName("Should throw exception when one or more choices are not valid options")
    fun shouldThrowExceptionWhenChoiceIsNotValid() {
        val survey = Survey(
            id = 1L,
            title = "Language Selection Survey",
            description = "Multiple choice test",
            items = mutableListOf(
                SurveyItem(1L, "Language", "Languages used", InputType.MULTI_CHOICE, true, survey = mock(), options = mutableListOf(
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

        assertEquals("You must enter a valid answer for the selected options.", exception.message)
    }
}

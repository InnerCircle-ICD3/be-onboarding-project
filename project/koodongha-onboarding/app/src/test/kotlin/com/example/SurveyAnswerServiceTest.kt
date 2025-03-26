package com.example

import com.example.dto.AnswerDto
import com.example.dto.AnswerSubmitDto
import com.example.entity.*
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
        val singleChoiceItem = SurveyItem(1L, "Language Choice", "Choose a language", InputType.SINGLE_CHOICE, true, survey = survey)
        val shortTextItem = SurveyItem(2L, "Experience", "Years of experience", InputType.SHORT_TEXT, false, survey = survey)
        val kotlinOption = SelectionOption(value = "Kotlin", surveyItem = singleChoiceItem)
        val javaOption = SelectionOption(value = "Java", surveyItem = singleChoiceItem)
        singleChoiceItem.options.addAll(listOf(kotlinOption, javaOption))
        survey.items.addAll(listOf(singleChoiceItem, shortTextItem))

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                AnswerDto(1L, listOf("Kotlin")),
                AnswerDto(2L, listOf("3 years"))
            )
        )

        val captor = argumentCaptor<List<SurveyAnswer>>()
        surveyAnswerService.submitAnswer(1L, request)
        verify(surveyAnswerRepository).saveAll(captor.capture())

        val savedAnswers = captor.firstValue
        val selectedOptionValues = savedAnswers.first { it.surveyItem.id == 1L }.selectedOptions.map { it.value }

        assertTrue(selectedOptionValues.contains("Kotlin"))
        assertEquals("3 years", savedAnswers.first { it.surveyItem.id == 2L }.shortAnswer)
    }

    @Test
    @DisplayName("Should throw exception when answer value does not match survey item options")
    fun shouldThrowExceptionWhenAnswerValueDoesNotMatchSurveyItemOptions() {
        val survey = Survey(
            id = 1L,
            title = "Answer Value Match Test",
            description = "Test if the answer matches the options"
        )
        val item = SurveyItem(1L, "Language Choice", "Choose a language", InputType.SINGLE_CHOICE, true, survey = survey)
        item.options.addAll(
            listOf(
                SelectionOption(value = "Kotlin", surveyItem = item),
                SelectionOption(value = "Java", surveyItem = item)
            )
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

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
        whenever(surveyRepository.findById(999L)).thenReturn(Optional.empty())

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
            description = "Select all the languages you use"
        )
        val item = SurveyItem(1L, "Language Choice", "Select all the languages you use", InputType.MULTI_CHOICE, true, survey = survey)
        val kotlin = SelectionOption(value = "Kotlin", surveyItem = item)
        val java = SelectionOption(value = "Java", surveyItem = item)
        item.options.addAll(listOf(kotlin, java))
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(AnswerDto(1L, listOf("Kotlin", "Java")))
        )

        val captor = argumentCaptor<List<SurveyAnswer>>()
        surveyAnswerService.submitAnswer(1L, request)
        verify(surveyAnswerRepository).saveAll(captor.capture())

        val savedOptions = captor.firstValue.first().selectedOptions.map { it.value }
        assertTrue(savedOptions.containsAll(listOf("Kotlin", "Java")))
    }

    @Test
    @DisplayName("Should throw exception when one or more choices are not valid options")
    fun shouldThrowExceptionWhenChoiceIsNotValid() {
        val survey = Survey(
            id = 1L,
            title = "Language Selection Survey",
            description = "Multiple choice test"
        )
        val item = SurveyItem(1L, "Language", "Languages used", InputType.MULTI_CHOICE, true, survey = survey)
        item.options.addAll(
            listOf(
                SelectionOption(value = "Kotlin", surveyItem = item),
                SelectionOption(value = "Java", surveyItem = item),
                SelectionOption(value = "Python", surveyItem = item)
            )
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(AnswerDto(1L, listOf("Kotlin", "Scala")))
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyAnswerService.submitAnswer(1L, request)
        }

        assertEquals("You must enter a valid answer for the selected options.", exception.message)
    }
}

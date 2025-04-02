package com.example

import com.example.exception.*
import com.example.dto.*
import com.example.entity.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.UpdateSurveyService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

class UpdateSurveyServiceTest {

    private val surveyRepository: SurveyRepository = mock()
    private val surveyAnswerRepository: SurveyAnswerRepository = mock()
    private val updateSurveyService = UpdateSurveyService(surveyRepository, surveyAnswerRepository)

    @Test
    @DisplayName("Should save successfully when submitting valid answers")
    fun shouldSaveSuccessfullyWhenSubmittingValidAnswers() {
        val singleChoiceItem = ChoiceItem(
            name = "Language Choice",
            description = "Favorite Language",
            isRequired = true,
            isMultiple = false,
            survey = mock()
        ).apply { id = 1L }

        val kotlinOption = SelectionOption(id = 10L, value = "Kotlin", item = singleChoiceItem)
        singleChoiceItem.options.add(kotlinOption)

        val survey = Survey(
            id = 1L,
            title = "Survey",
            description = "Test",
            items = mutableListOf(singleChoiceItem)
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = 1L, selectedOptionIds = listOf(10L))
            )
        )

        assertDoesNotThrow {
            updateSurveyService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(argumentCaptor<List<SurveyAnswerBase>>().capture())
    }

    @Test
    @DisplayName("Should throw exception when survey does not exist")
    fun shouldThrowExceptionWhenSurveyDoesNotExist() {
        whenever(surveyRepository.findById(999L)).thenReturn(Optional.empty())

        val request = AnswerSubmitDto(
            answers = listOf(TextAnswerDto(itemId = 1L, value = "Kotlin"))
        )

        val exception = assertThrows(SurveyNotFoundException::class.java) {
            updateSurveyService.submitAnswer(999L, request)
        }

        assertEquals("설문을 찾을 수 없습니다.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when answer item does not match survey item")
    fun shouldThrowExceptionWhenAnswerItemDoesNotMatchSurveyItem() {
        val item = ChoiceItem(
            name = "Language Choice",
            description = "Favorite Language",
            isRequired = true,
            isMultiple = false,
            survey = mock()
        ).apply { id = 1L }

        val survey = Survey(
            id = 1L,
            title = "Survey",
            description = "Test",
            items = mutableListOf(item)
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                ChoiceAnswerDto(itemId = 999L, selectedOptionIds = listOf(10L))
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            updateSurveyService.submitAnswer(1L, request)
        }

        assertEquals("Answer value does not match survey item.", exception.message)
    }

    @Test
    @DisplayName("Should save successfully when input type is LONG_TEXT")
    fun shouldSaveSuccessfullyWhenInputTypeIsLongText() {
        val longTextItem = TextItem(
            name = "Self-Introduction",
            description = "Please explain in detail",
            isRequired = true,
            isLong = true,
            survey = mock()
        ).apply { id = 1L }

        val survey = Survey(
            id = 1L,
            title = "Self-Introduction Survey",
            description = "Please write a detailed self-introduction",
            items = mutableListOf(longTextItem)
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                TextAnswerDto(itemId = 1L, value = "I am a backend developer, primarily using Kotlin and Java!")
            )
        )

        assertDoesNotThrow {
            updateSurveyService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(argumentCaptor<List<SurveyAnswerBase>>().capture())
    }

    @Test
    @DisplayName("Should throw exception when SHORT_TEXT answer is too long")
    fun shouldThrowExceptionWhenShortTextAnswerIsTooLong() {
        val shortTextItem = TextItem(
            name = "One Line Introduction",
            description = "Please introduce yourself in one line",
            isRequired = true,
            isLong = false,
            survey = mock()
        ).apply { id = 1L }

        val survey = Survey(
            id = 1L,
            title = "Short Answer Test",
            description = "Check for maximum length",
            items = mutableListOf(shortTextItem)
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val longText = "a".repeat(300)

        val request = AnswerSubmitDto(
            answers = listOf(TextAnswerDto(itemId = 1L, value = longText))
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            updateSurveyService.submitAnswer(1L, request)
        }

        assertEquals("SHORT_TEXT answers must be within 255 characters.", exception.message)
    }

    @Test
    @DisplayName("Should keep existing answers when items are updated")
    fun shouldKeepExistingAnswersWhenItemsAreUpdated() {
        val textItem = TextItem(
            name = "Old Question",
            description = "Old Description",
            isRequired = true,
            isLong = false,
            survey = mock()
        ).apply { id = 1L }

        val updatedItem = TextItem(
            name = "Updated Question",
            description = "Updated Description",
            isRequired = true,
            isLong = false,
            survey = mock()
        ).apply { id = 2L }

        val survey = Survey(
            id = 1L,
            title = "Editable Survey",
            description = "Before update",
            items = mutableListOf(textItem, updatedItem)
        )

        val existingAnswer = TextAnswer(
            content = "Old Answer",
            survey = survey,
            item = textItem
        )

        textItem.answers.add(existingAnswer)
        survey.items.addAll(listOf(textItem, updatedItem))

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = AnswerSubmitDto(
            answers = listOf(
                TextAnswerDto(itemId = 2L, value = "New Answer")
            )
        )

        assertDoesNotThrow {
            updateSurveyService.submitAnswer(1L, request)
        }

        verify(surveyAnswerRepository).saveAll(argumentCaptor<List<SurveyAnswerBase>>().capture())
        assertTrue(textItem.answers.contains(existingAnswer))
    }
}

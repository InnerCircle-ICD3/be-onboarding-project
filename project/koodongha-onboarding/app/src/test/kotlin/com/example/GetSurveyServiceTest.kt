package com.example

import com.example.dto.SurveyResponse
import com.example.entity.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.GetSurveyService
import com.example.common.exception.SurveyNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.Optional

class GetSurveyServiceTest {

    private val surveyRepository: SurveyRepository = mock()
    private val answerRepository: SurveyAnswerRepository = mock()
    private val service = GetSurveyService(surveyRepository, answerRepository)

    @Test
    @DisplayName("Should return survey successfully when survey exists")
    fun shouldReturnSurveyWhenExists() {
        val surveyId = 1L
        val survey = Survey(surveyId, "Test Survey", "Survey Description")
        val item = SurveyItem(
            id = 1L,
            name = "Language Choice",
            description = "Choose a language",
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
            selectedOptions = mutableListOf(option)
        )

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(answer))

        val result: SurveyResponse = service.getSurvey(surveyId)

        assertEquals("Test Survey", result.title)
        assertEquals(1, result.items.size)
        assertEquals("Language Choice", result.items[0].name)
        assertEquals("Kotlin", result.items[0].answers?.first())
    }

    @Test
    @DisplayName("Should throw exception when survey does not exist")
    fun shouldThrowExceptionWhenSurveyNotFound() {
        whenever(surveyRepository.findById(9999L)).thenReturn(Optional.empty())

        val exception = assertThrows(SurveyNotFoundException::class.java) {
            service.getSurvey(9999L)
        }

        assertEquals("설문을 찾을 수 없습니다.", exception.message)
    }

    @Test
    @DisplayName("Should return empty answer list when no answers exist for a survey item")
    fun shouldReturnEmptyAnswerListWhenNoAnswersExist() {
        val surveyId = 2L
        val survey = Survey(surveyId, "Empty Answer Survey", "No responses")
        val item = SurveyItem(
            id = 1L,
            name = "Hobby",
            description = "Enter your hobby",
            inputType = InputType.SHORT_TEXT,
            isRequired = false,
            survey = survey
        )
        survey.items.add(item)

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(emptyList())

        val result = service.getSurvey(surveyId)

        assertEquals("Empty Answer Survey", result.title)
        assertEquals(1, result.items.size)
        assertTrue(result.items[0].answers?.isEmpty() == true)
    }

    @Test
    @DisplayName("Should filter answers by item name and answer value")
    fun shouldFilterAnswersByItemNameAndAnswerValue() {
        val surveyId = 3L
        val survey = Survey(surveyId, "Filter Test", "Filter by specific item")
        val item = SurveyItem(
            id = 1L,
            name = "Framework",
            description = "Preferred framework",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = true,
            survey = survey
        )
        val option1 = SelectionOption(value = "Spring", surveyItem = item)
        val option2 = SelectionOption(value = "Django", surveyItem = item)
        item.options.addAll(listOf(option1, option2))
        survey.items.add(item)

        val answer1 = SurveyAnswer(survey = survey, surveyItem = item, selectedOptions = mutableListOf(option1))
        val answer2 = SurveyAnswer(survey = survey, surveyItem = item, selectedOptions = mutableListOf(option2))

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(answer1, answer2))

        val result = service.getSurvey(surveyId, filterName = "Framework", filterAnswer = "Spring")

        assertEquals(1, result.items.size)
        assertEquals("Framework", result.items[0].name)
        assertTrue(result.items[0].answers!!.contains("Spring"))
        assertFalse(result.items[0].answers!!.contains("Django"))
    }

    @Test
    @DisplayName("Should exclude items if filter conditions do not match")
    fun shouldExcludeItemsWhenFilterConditionsDoNotMatch() {
        val surveyId = 4L
        val survey = Survey(surveyId, "Filter Exclude Test", "Condition mismatch")
        val item = SurveyItem(
            id = 1L,
            name = "OS",
            description = "Operating system",
            inputType = InputType.SINGLE_CHOICE,
            isRequired = false,
            survey = survey
        )
        val option = SelectionOption(value = "Linux", surveyItem = item)
        item.options.add(option)
        survey.items.add(item)

        val answer = SurveyAnswer(survey = survey, surveyItem = item, selectedOptions = mutableListOf(option))

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(answer))

        val result = service.getSurvey(surveyId, filterName = "OS", filterAnswer = "Windows")

        assertTrue(result.items.isEmpty())
    }
}

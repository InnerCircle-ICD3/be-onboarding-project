package com.example

import com.example.dto.SurveyResponse
import com.example.entity.*
import com.example.exception.SurveyNotFoundException
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.GetSurveyService
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

        val textItem = TextItem(
            survey = survey,
            name = "Language Choice",
            description = "Choose a language",
            isRequired = true,
            isLong = false
        )
        textItem.id = 1L

        val choiceItem = ChoiceItem(
            survey = survey,
            name = "Preferred Framework",
            description = "Choose a framework",
            isRequired = true,
            isMultiple = false
        )
        choiceItem.id = 2L

        val option = SelectionOption(value = "Kotlin", item = choiceItem)
        option.id = 10L
        choiceItem.options.add(option)

        survey.items.addAll(listOf(textItem, choiceItem))

        val choiceAnswer = ChoiceAnswer(
            survey = survey,
            item = choiceItem,
            selectedOptions = mutableListOf(option)
        )

        val textAnswer = TextAnswer(
            survey = survey,
            item = textItem,
            content = "Java"
        )

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(choiceAnswer, textAnswer))

        val result: SurveyResponse = service.getSurvey(surveyId)

        assertEquals("Test Survey", result.title)
        assertEquals(2, result.items.size)

        val textItemResp = result.items.find { it.name == "Language Choice" }
        val choiceItemResp = result.items.find { it.name == "Preferred Framework" }

        assertEquals(listOf("Java"), textItemResp?.answers)
        assertEquals(listOf("Kotlin"), choiceItemResp?.answers)
    }

    @Test
    @DisplayName("Should throw exception when survey does not exist")
    fun shouldThrowExceptionWhenSurveyNotFound() {
        whenever(surveyRepository.findById(9999L)).thenReturn(Optional.empty())

        val exception = assertThrows(SurveyNotFoundException::class.java) {
            service.getSurvey(9999L)
        }

        assertEquals(SurveyNotFoundException().message, exception.message)
    }

    @Test
    @DisplayName("Should return empty answer list when no answers exist for a survey item")
    fun shouldReturnEmptyAnswerListWhenNoAnswersExist() {
        val surveyId = 2L
        val survey = Survey(surveyId, "Empty Answer Survey", "No responses")

        val textItem = TextItem(
            survey = survey,
            name = "Hobby",
            description = "Enter your hobby",
            isRequired = false,
            isLong = false
        )
        survey.items.add(textItem)

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(emptyList())

        val result = service.getSurvey(surveyId)

        assertEquals("Empty Answer Survey", result.title)
        assertEquals(1, result.items.size)
        assertTrue(result.items[0].answers!!.isEmpty())
    }

    @Test
    @DisplayName("Should filter answers by item name and answer value")
    fun shouldFilterAnswersByItemNameAndAnswerValue() {
        val surveyId = 3L
        val survey = Survey(surveyId, "Filter Test", "Filter by specific item")

        val choiceItem = ChoiceItem(
            survey = survey,
            name = "Framework",
            description = "Preferred framework",
            isRequired = true,
            isMultiple = false
        )

        val option1 = SelectionOption(value = "Spring", item = choiceItem)
        val option2 = SelectionOption(value = "Django", item = choiceItem)
        choiceItem.options.addAll(listOf(option1, option2))
        survey.items.add(choiceItem)

        val answer1 = ChoiceAnswer(survey = survey, item = choiceItem, selectedOptions = mutableListOf(option1))
        val answer2 = ChoiceAnswer(survey = survey, item = choiceItem, selectedOptions = mutableListOf(option2))

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(answer1, answer2))

        val result = service.getSurvey(surveyId, filterName = "Framework", filterAnswer = "Spring")

        assertEquals(1, result.items.size)
        assertEquals("Framework", result.items[0].name)
        assertEquals(listOf("Spring"), result.items[0].answers)
    }

    @Test
    @DisplayName("Should exclude items if filter conditions do not match")
    fun shouldExcludeItemsWhenFilterConditionsDoNotMatch() {
        val surveyId = 4L
        val survey = Survey(surveyId, "Filter Exclude Test", "Condition mismatch")

        val choiceItem = ChoiceItem(
            survey = survey,
            name = "OS",
            description = "Operating system",
            isRequired = false,
            isMultiple = false
        )
        val option = SelectionOption(value = "Linux", item = choiceItem)
        choiceItem.options.add(option)
        survey.items.add(choiceItem)

        val answer = ChoiceAnswer(survey = survey, item = choiceItem, selectedOptions = mutableListOf(option))

        whenever(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))
        whenever(answerRepository.findBySurveyId(surveyId)).thenReturn(listOf(answer))

        val result = service.getSurvey(surveyId, filterName = "OS", filterAnswer = "Windows")

        assertTrue(result.items.isEmpty())
    }
}

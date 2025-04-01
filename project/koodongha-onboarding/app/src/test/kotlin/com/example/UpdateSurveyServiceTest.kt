package com.example

import com.example.dto.*
import com.example.entity.*
import com.example.exception.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.service.UpdateSurveyService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Optional

class UpdateSurveyServiceTest {

    private val surveyRepository: SurveyRepository = mock()
    private val surveyAnswerRepository: SurveyAnswerRepository = mock()
    private val updateSurveyService = UpdateSurveyService(surveyRepository, surveyAnswerRepository)

    @Test
    @DisplayName("Should update survey title and description")
    fun shouldUpdateSurveyTitleAndDescription() {
        val survey = Survey(
            id = 1L,
            title = "Old Title",
            description = "Old Description",
            items = mutableListOf()
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val request = SurveyUpdateRequest(
            title = "New Title",
            description = "New Description",
            items = listOf()
        )

        updateSurveyService.updateSurvey(1L, request)

        assertEquals("New Title", survey.title)
        assertEquals("New Description", survey.description)
    }

    @Test
    @DisplayName("Should replace all survey items with updated ones")
    fun shouldReplaceAllSurveyItems() {
        val oldItem = TextItem(
            name = "Old Item",
            description = "Old Description",
            isRequired = true,
            isLong = false,
            survey = mock()
        ).apply { id = 1L }

        val survey = Survey(
            id = 1L,
            title = "Survey",
            description = "Survey Desc",
            items = mutableListOf(oldItem)
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val newRequestItem = TextItemUpdateRequest(
            id = 1L,
            name = "Updated Item",
            description = "Updated Description",
            isRequired = false,
            isLong = true
        )

        val request = SurveyUpdateRequest(
            title = "Survey",
            description = "Survey Desc",
            items = listOf(newRequestItem)
        )

        updateSurveyService.updateSurvey(1L, request)

        val updatedItem = survey.items.first() as TextItem
        assertEquals("Updated Item", updatedItem.name)
        assertEquals("Updated Description", updatedItem.description)
        assertFalse(updatedItem.isRequired)
        assertTrue(updatedItem.isLong)
    }

    @Test
    @DisplayName("Should add new item when no ID is provided")
    fun shouldAddNewItemWhenIdIsNull() {
        val survey = Survey(
            id = 1L,
            title = "Survey",
            description = "Survey Desc",
            items = mutableListOf()
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val newRequestItem = ChoiceItemUpdateRequest(
            id = null,
            name = "New Choice",
            description = "Pick one",
            isRequired = true,
            isMultiple = false,
            options = listOf("A", "B")
        )

        val request = SurveyUpdateRequest(
            title = "Survey",
            description = "Survey Desc",
            items = listOf(newRequestItem)
        )

        updateSurveyService.updateSurvey(1L, request)

        assertEquals(1, survey.items.size)
        val newItem = survey.items.first() as ChoiceItem
        assertEquals("New Choice", newItem.name)
        assertEquals(2, newItem.options.size)
    }

    @Test
    @DisplayName("Should remove deleted items that are not in update request")
    fun shouldRemoveDeletedItems() {
        val keptItem = TextItem(
            name = "Keep me",
            description = "I'm staying",
            isRequired = true,
            isLong = false,
            survey = mock()
        ).apply { id = 1L }

        val removedItem = TextItem(
            name = "Remove me",
            description = "I'm going",
            isRequired = false,
            isLong = false,
            survey = mock()
        ).apply { id = 2L }

        val survey = Survey(
            id = 1L,
            title = "Survey",
            description = "Survey Desc",
            items = mutableListOf(keptItem, removedItem)
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val updateRequest = TextItemUpdateRequest(
            id = 1L,
            name = "Keep me updated",
            description = "Still here",
            isRequired = true,
            isLong = true
        )

        val request = SurveyUpdateRequest(
            title = "Survey",
            description = "Survey Desc",
            items = listOf(updateRequest)
        )

        updateSurveyService.updateSurvey(1L, request)

        assertEquals(1, survey.items.size)
        assertEquals("Keep me updated", survey.items[0].name)
    }

    @Test
    @DisplayName("Should throw when item type mismatch on update")
    fun shouldThrowWhenItemTypeMismatch() {
        val originalItem = ChoiceItem(
            name = "Pick one",
            description = "Choices",
            isRequired = true,
            isMultiple = false,
            survey = mock()
        ).apply { id = 1L }

        val survey = Survey(
            id = 1L,
            title = "Survey",
            description = "Survey Desc",
            items = mutableListOf(originalItem)
        )

        whenever(surveyRepository.findById(1L)).thenReturn(Optional.of(survey))

        val invalidUpdate = TextItemUpdateRequest(
            id = 1L,
            name = "Invalid",
            description = "Oops",
            isRequired = true,
            isLong = false
        )

        val request = SurveyUpdateRequest(
            title = "Survey",
            description = "Survey Desc",
            items = listOf(invalidUpdate)
        )

        val ex = assertThrows(InvalidSurveyRequestException::class.java) {
            updateSurveyService.updateSurvey(1L, request)
        }

        assertEquals("Invalid item type.", ex.message)
    }
}
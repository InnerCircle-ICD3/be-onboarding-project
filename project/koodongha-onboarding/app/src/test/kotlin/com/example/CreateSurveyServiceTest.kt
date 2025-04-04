package com.example

import com.example.dto.*
import com.example.entity.*
import com.example.exception.*
import com.example.repository.SurveyRepository
import com.example.service.CreateSurveyService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class CreateSurveyServiceTest {

    private val surveyRepository = mock<SurveyRepository>()
    private val createSurveyService = CreateSurveyService(surveyRepository)

    @Test
    @DisplayName("Should create survey successfully when items are between 1 and 10")
    fun shouldCreateSurveyWhenItemsBetween1And10() {
        val request = CreateSurveyRequest(
            title = "Title",
            description = "Description",
            items = (1..10).map {
                TextItemRequest(
                    name = "Question$it",
                    description = "Description$it",
                    isRequired = true,
                    isLong = false
                )
            }
        )

        assertDoesNotThrow { createSurveyService.createSurvey(request) }
        verify(surveyRepository).save(any())
    }

    @Test
    @DisplayName("Should throw exception when items exceed 10")
    fun shouldThrowExceptionWhenItemsExceed10() {
        val request = CreateSurveyRequest(
            title = "Title",
            description = "Description",
            items = (1..11).map {
                TextItemRequest(
                    name = "Question$it",
                    description = "Description$it",
                    isRequired = true,
                    isLong = false
                )
            }
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("Survey items must be between 1 and 10.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when choice type option is null")
    fun shouldThrowExceptionWhenChoiceTypeOptionIsNull() {
        val request = CreateSurveyRequest(
            title = "Title",
            description = "Description",
            items = listOf(
                ChoiceItemRequest(
                    name = "Language Choice",
                    description = "No options",
                    isRequired = true,
                    isMultiple = false,
                    options = emptyList()
                )
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("Choice-type items must have options.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when choice type option is empty")
    fun shouldThrowExceptionWhenChoiceTypeOptionIsEmpty() {
        val request = CreateSurveyRequest(
            title = "Title",
            description = "Description",
            items = listOf(
                ChoiceItemRequest(
                    name = "Language Choice",
                    description = "Empty options list",
                    isRequired = true,
                    isMultiple = true,
                    options = emptyList()
                )
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("Choice-type items must have options.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when item name is empty")
    fun shouldThrowExceptionWhenItemNameIsEmpty() {
        val request = CreateSurveyRequest(
            title = "Title",
            description = "Description",
            items = listOf(
                TextItemRequest(
                    name = "",
                    description = "Description",
                    isRequired = true,
                    isLong = false
                )
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("Item name is required.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when item description is empty")
    fun shouldThrowExceptionWhenItemDescriptionIsEmpty() {
        val request = CreateSurveyRequest(
            title = "Title",
            description = "Description",
            items = listOf(
                TextItemRequest(
                    name = "Question 1",
                    description = "",
                    isRequired = true,
                    isLong = false
                )
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("Item description is required.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when survey title is empty")
    fun shouldThrowExceptionWhenTitleIsEmpty() {
        val request = CreateSurveyRequest(
            title = "",
            description = "Description",
            items = listOf(
                TextItemRequest(
                    name = "Question 1",
                    description = "Description",
                    isRequired = true,
                    isLong = false
                )
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("Survey title is required.", exception.message)
    }

    @Test
    @DisplayName("Should throw exception when survey description is empty")
    fun shouldThrowExceptionWhenDescriptionIsEmpty() {
        val request = CreateSurveyRequest(
            title = "Title",
            description = "",
            items = listOf(
                TextItemRequest(
                    name = "Question 1",
                    description = "Description",
                    isRequired = true,
                    isLong = false
                )
            )
        )

        val exception = assertThrows(InvalidSurveyRequestException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("Survey description is required.", exception.message)
    }
}

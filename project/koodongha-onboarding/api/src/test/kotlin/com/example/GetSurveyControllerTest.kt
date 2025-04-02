package com.example

import com.example.dto.*
import com.example.controller.GetSurveyController
import com.example.service.GetSurveyService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus

class GetSurveyControllerTest {

    private val getSurveyService: GetSurveyService = mock()
    private val getSurveyController = GetSurveyController(getSurveyService)

    @Test
    @DisplayName("Should return survey when no filters are provided")
    fun shouldReturnSurveyWithoutFilters() {
        val expectedResponse = SurveyResponse(
            id = 1L,
            title = "Title",
            description = "Description",
            items = emptyList()
        )

        whenever(getSurveyService.getSurvey(1L, null, null)).thenReturn(expectedResponse)

        val response = getSurveyController.getSurvey(1L, null, null)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedResponse, response.body)
    }

    @Test
    @DisplayName("Should return survey when only filterName is provided")
    fun shouldReturnSurveyWithFilterNameOnly() {
        val expectedResponse = SurveyResponse(
            id = 1L,
            title = "Title",
            description = "Description",
            items = listOf(
                TextItemResponse(
                    id = 1L,
                    name = "Language",
                    description = null,
                    isRequired = true,
                    isLong = false,
                    answers = listOf("Kotlin")
                )
            )
        )

        whenever(getSurveyService.getSurvey(1L, "Language", null)).thenReturn(expectedResponse)

        val response = getSurveyController.getSurvey(1L, "Language", null)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedResponse, response.body)
    }

    @Test
    @DisplayName("Should return survey when only filterAnswer is provided")
    fun shouldReturnSurveyWithFilterAnswerOnly() {
        val expectedResponse = SurveyResponse(
            id = 1L,
            title = "Title",
            description = "Description",
            items = listOf(
                TextItemResponse(
                    id = 2L,
                    name = "Comment",
                    description = null,
                    isRequired = false,
                    isLong = true,
                    answers = listOf("Kotlin")
                )
            )
        )

        whenever(getSurveyService.getSurvey(1L, null, "Kotlin")).thenReturn(expectedResponse)

        val response = getSurveyController.getSurvey(1L, null, "Kotlin")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedResponse, response.body)
    }

    @Test
    @DisplayName("Should return survey when both filterName and filterAnswer are provided")
    fun shouldReturnSurveyWithBothFilters() {
        val expectedResponse = SurveyResponse(
            id = 1L,
            title = "Title",
            description = "Description",
            items = listOf(
                ChoiceItemResponse(
                    id = 3L,
                    name = "Tech Stack",
                    description = null,
                    isRequired = true,
                    isMultiple = false,
                    options = listOf("Kotlin"),
                    answers = listOf("Kotlin")
                )
            )
        )

        whenever(getSurveyService.getSurvey(1L, "Tech Stack", "Kotlin")).thenReturn(expectedResponse)

        val response = getSurveyController.getSurvey(1L, "Tech Stack", "Kotlin")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedResponse, response.body)
    }
}

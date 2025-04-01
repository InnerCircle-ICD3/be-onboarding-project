package com.example

import com.example.dto.SurveyItemResponse
import com.example.dto.SurveyResponse
import com.example.entity.Survey
import com.example.service.GetSurveyService
import com.example.api.controller.GetSurveyController
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

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
            items = listOf(SurveyItemResponse(id = 1L, name = "Language", description = null, type = "TEXT", isRequired = true, options = null))
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
            items = listOf(SurveyItemResponse(id = 2L, name = "Comment", description = null, type = "TEXT", isRequired = false, options = null))
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
            items = listOf(SurveyItemResponse(id = 3L, name = "Tech Stack", description = null, type = "CHOICE", isRequired = true, options = listOf("Kotlin")))
        )

        whenever(getSurveyService.getSurvey(1L, "Tech Stack", "Kotlin")).thenReturn(expectedResponse)

        val response = getSurveyController.getSurvey(1L, "Tech Stack", "Kotlin")

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedResponse, response.body)
    }
} 
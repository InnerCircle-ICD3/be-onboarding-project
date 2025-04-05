package com.example

import com.example.dto.*
import com.example.service.CreateSurveyService
import com.example.controller.CreateSurveyController
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.Runs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CreateSurveyController::class)
class CreateSurveyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var createSurveyService: CreateSurveyService

    private val objectMapper = ObjectMapper()

    @Test
    @DisplayName("Should create survey successfully with valid request")
    fun shouldCreateSurveySuccessfully() {
        val request = CreateSurveyRequest(
            title = "My First Survey",
            description = "Tell us your opinion",
            items = listOf(
                TextItemRequest(
                    name = "What's your name?",
                    description = "Please enter your full name",
                    isRequired = true,
                    isLong = false
                ),
                ChoiceItemRequest(
                    name = "Favorite language?",
                    description = "Select one",
                    isRequired = true,
                    isMultiple = false,
                    options = listOf("Kotlin", "Java", "Python")
                )
            )
        )

        every { createSurveyService.createSurvey(request) } just Runs

        mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status().isOk
        }
    }

    @Test
    @DisplayName("Should fail when title is blank")
    fun shouldFailWhenTitleIsBlank() {
        val request = CreateSurveyRequest(
            title = "",
            description = "Desc",
            items = listOf(
                TextItemRequest("Q1", "Desc", true, false)
            )
        )

        mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status().isBadRequest
        }
    }

    @Test
    @DisplayName("Should fail when items are empty")
    fun shouldFailWhenItemsAreEmpty() {
        val request = CreateSurveyRequest(
            title = "Survey",
            description = "Desc",
            items = emptyList()
        )

        mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status().isBadRequest
        }
    }

    @Test
    @DisplayName("Should fail when choice item has no options")
    fun shouldFailWhenChoiceItemHasNoOptions() {
        val request = CreateSurveyRequest(
            title = "Survey",
            description = "Desc",
            items = listOf(
                ChoiceItemRequest(
                    name = "Select one",
                    description = "Pick one",
                    isRequired = true,
                    isMultiple = false,
                    options = emptyList()
                )
            )
        )

        mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status().isBadRequest
        }
    }

    @Test
    @DisplayName("Should fail when items exceed 10")
    fun shouldFailWhenItemsExceedLimit() {
        val item = TextItemRequest("Q", "D", false, false)
        val request = CreateSurveyRequest(
            title = "Survey",
            description = "Desc",
            items = List(11) { item }
        )

        mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status().isBadRequest
        }
    }
}

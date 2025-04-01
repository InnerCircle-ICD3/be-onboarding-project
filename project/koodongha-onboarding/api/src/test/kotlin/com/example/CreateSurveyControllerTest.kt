package com.example

import com.example.dto.*
import com.example.service.CreateSurveyService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CreateSurveyController::class)
class CreateSurveyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var createSurveyService: CreateSurveyService

    private val objectMapper = ObjectMapper()

    @Test
    @DisplayName("Should create survey successfully with valid request")
    fun shouldCreateSurveySuccessfully() {
        val request = CreateSurveyRequest(
            title = "My First Survey",
            description = "Tell us your opinion",
            items = listOf(
                CreateTextItemRequest(
                    name = "What's your name?",
                    description = "Please enter your full name",
                    isRequired = true,
                    isLong = false
                ),
                CreateChoiceItemRequest(
                    name = "Favorite language?",
                    description = "Select one",
                    isRequired = true,
                    isMultiple = false,
                    options = listOf("Kotlin", "Java", "Python")
                )
            )
        )

        doNothing().`when`(createSurveyService).createSurvey(any())

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
                CreateTextItemRequest("Q1", "Desc", true, false)
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
                CreateChoiceItemRequest(
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
        val item = CreateTextItemRequest("Q", "D", false, false)
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

package com.onboarding.form.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.onboarding.form.domain.QuestionType
import com.onboarding.form.request.CreateSelectQuestionDto
import com.onboarding.form.request.CreateStandardQuestionDto
import com.onboarding.form.request.CreateSurveyDto
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class SurveyControllerTest{
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("Controller 요청시 성공 응답이 내려오는지 테스트")
    fun createSurvey(){
        val createSurveyDto = CreateSurveyDto(
            title = "testTitle",
            description = "testDescription",
            questions =  listOf(
                CreateSelectQuestionDto(
                    title = "testTitle",
                    description = "testDescription",
                    type = QuestionType.MULTI_SELECT,
                    isRequired = true,
                    answerList =  listOf("testAnswer1", "testAnswer2")
                ),
                CreateSelectQuestionDto(
                    title = "testTitle",
                    description = "testDescription",
                    type = QuestionType.SINGLE_SELECT,
                    isRequired = true,
                    answerList =  listOf("testAnswer1", "testAnswer2")
                ),
                CreateStandardQuestionDto(
                    title = "testTitle",
                    description = "testDescription",
                    type = QuestionType.SHORT,
                    isRequired = true,
                ),
                CreateStandardQuestionDto(
                    title = "testTitle",
                    description = "testDescription",
                    type = QuestionType.LONG,
                    isRequired = true,
                )
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/surveys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createSurveyDto))
        ).andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.title").value(createSurveyDto.title)
            )
    }
}
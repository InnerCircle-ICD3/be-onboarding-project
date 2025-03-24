package com.example.controller

import com.example.dto.CreateSurveyItemRequest
import com.example.dto.CreateSurveyRequest
import com.example.entity.InputType
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SurveyControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `설문 응답 제출 성공`() {
        val createRequest = CreateSurveyRequest(
            title = "응답 테스트 설문",
            description = "응답을 제출하고 조회하는 테스트",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "언어를 선택하세요",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Kotlin", "Java")
                ),
                CreateSurveyItemRequest(
                    name = "경력",
                    description = "몇 년 하셨나요?",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = false
                )
            )
        )

        val createJson = objectMapper.writeValueAsString(createRequest)
        val result = mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = createJson
        }.andExpect {
            status { isCreated() }
        }.andReturn()

        val surveyId = objectMapper.readTree(result.response.contentAsString).get("id").asLong()

        val answerRequest = mapOf(
            "answers" to mapOf(
                "1" to "Kotlin",
                "2" to "3년"
            )
        )

        val answerJson = objectMapper.writeValueAsString(answerRequest)

        mockMvc.post("/surveys/$surveyId/answers") {
            contentType = MediaType.APPLICATION_JSON
            content = answerJson
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `응답 값이 설문 항목과 일치하지 않으면 예외 발생`() {
        val createRequest = CreateSurveyRequest(
            title = "응답 값 일치 테스트",
            description = "응답 값이 일치하지 않으면 예외가 발생하는지 확인",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "언어를 선택하세요",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Kotlin", "Java")
                )
            )
        )

        val createJson = objectMapper.writeValueAsString(createRequest)
        val result = mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = createJson
        }.andExpect {
            status { isCreated() }
        }.andReturn()

        val surveyId = objectMapper.readTree(result.response.contentAsString).get("id").asLong()

        val answerRequest = mapOf(
            "answers" to mapOf(
                "1" to "Python" // 잘못된 응답 값
            )
        )

        val answerJson = objectMapper.writeValueAsString(answerRequest)

        mockMvc.post("/surveys/$surveyId/answers") {
            contentType = MediaType.APPLICATION_JSON
            content = answerJson
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.message") { value("응답 값이 설문 항목과 일치하지 않습니다.") }
        }
    }

    @Test
    fun `응답 항목의 입력 형태와 일치하지 않으면 예외 발생`() {
        val createRequest = CreateSurveyRequest(
            title = "입력 형태 일치 테스트",
            description = "응답 항목의 입력 형태와 일치하지 않으면 예외가 발생하는지 확인",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "경력",
                    description = "경력을 입력하세요",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true
                ),
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "언어를 선택하세요",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Kotlin", "Java")
                )
            )
        )

        val createJson = objectMapper.writeValueAsString(createRequest)
        val result = mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = createJson
        }.andExpect {
            status { isCreated() }
        }.andReturn()

        val surveyId = objectMapper.readTree(result.response.contentAsString).get("id").asLong()

        val answerRequest = mapOf(
            "answers" to mapOf(
                "1" to "3년",  // SHORT_TEXT에 적합한 값
                "2" to "Python"  // 선택형 항목에 잘못된 응답
            )
        )

        val answerJson = objectMapper.writeValueAsString(answerRequest)

        mockMvc.post("/surveys/$surveyId/answers") {
            contentType = MediaType.APPLICATION_JSON
            content = answerJson
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.message") { value("선택형 항목에 적합한 응답 값을 입력해야 합니다.") }
        }
    }
}

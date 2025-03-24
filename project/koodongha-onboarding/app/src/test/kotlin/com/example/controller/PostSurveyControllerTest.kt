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
import org.springframework.test.web.servlet.put
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
    fun `설문조사 생성 후 수정 성공`() {
        val createRequest = CreateSurveyRequest(
            title = "백엔드 기술 설문",
            description = "Kotlin과 Java를 비교하는 설문입니다.",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "선호하는 언어를 골라주세요",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Java", "Kotlin", "Python")
                )
            )
        )

        val json = objectMapper.writeValueAsString(createRequest)

        val result = mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isCreated() }
        }.andReturn()

        val responseJson = result.response.contentAsString
        val createdId = objectMapper.readTree(responseJson).get("id").asLong()

        val updateRequest = CreateSurveyRequest(
            title = "수정된 설문",
            description = "수정된 설명",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "변경된 질문",
                    description = "변경된 설명",
                    inputType = InputType.LONG_TEXT,
                    isRequired = false
                )
            )
        )

        mockMvc.put("/surveys/$createdId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateRequest)
        }.andExpect {
            status { isOk() }
            jsonPath("$.title") { value("수정된 설문") }
            jsonPath("$.description") { value("수정된 설명") }
            jsonPath("$.items[0].name") { value("변경된 질문") }
        }
    }

    @Test
    fun `설문 항목 추가 후 기존 응답이 유지되는지 확인`() {
        val createRequest = CreateSurveyRequest(
            title = "응답 유지 테스트 설문",
            description = "응답을 수정해도 유지되는지 확인하는 테스트",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "선호하는 언어를 선택하세요",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Java", "Kotlin", "Python")
                )
            )
        )

        val json = objectMapper.writeValueAsString(createRequest)

        val result = mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isCreated() }
        }.andReturn()

        val responseJson = result.response.contentAsString
        val createdId = objectMapper.readTree(responseJson).get("id").asLong()

        val answerRequest = mapOf(
            "answers" to mapOf(
                "1" to "Kotlin"
            )
        )

        val answerJson = objectMapper.writeValueAsString(answerRequest)

        mockMvc.post("/surveys/$createdId/answers") {
            contentType = MediaType.APPLICATION_JSON
            content = answerJson
        }.andExpect {
            status { isOk() }
        }

        val updateRequest = CreateSurveyRequest(
            title = "수정된 설문",
            description = "수정된 설명",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "선호하는 언어를 선택하세요",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Java", "Kotlin", "Python", "Go")
                ),
                CreateSurveyItemRequest(
                    name = "프로그래밍 경험",
                    description = "몇 년 동안 프로그래밍을 해보셨나요?",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = false
                )
            )
        )

        mockMvc.put("/surveys/$createdId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateRequest)
        }.andExpect {
            status { isOk() }
            jsonPath("$.title") { value("수정된 설문") }
            jsonPath("$.items[0].options[3]") { value("Go") }
            jsonPath("$.items[1].name") { value("프로그래밍 경험") }
        }

        mockMvc.get("/surveys/$createdId/answers")
            .andExpect {
                status { isOk() }
                jsonPath("$[0].answers['1']") { value("Kotlin") }
            }
    }

    @Test
    fun `잘못된 설문 수정 시 예외 처리`() {
        val createRequest = CreateSurveyRequest(
            title = "응답 유지 테스트 설문",
            description = "응답을 수정해도 유지되는지 확인하는 테스트",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "선호하는 언어를 선택하세요",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Java", "Kotlin", "Python")
                )
            )
        )

        val json = objectMapper.writeValueAsString(createRequest)

        val result = mockMvc.post("/surveys") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }.andExpect {
            status { isCreated() }
        }.andReturn()

        val responseJson = result.response.contentAsString
        val createdId = objectMapper.readTree(responseJson).get("id").asLong()

        val invalidUpdateRequest = CreateSurveyRequest(
            title = "수정된 설문",
            description = "수정된 설명",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "",
                    description = "빈 항목 이름",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = listOf("Java", "Kotlin")
                )
            )
        )

        val exception = mockMvc.put("/surveys/$createdId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidUpdateRequest)
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.message") { value("항목 이름은 필수입니다.") }
        }
    }
}

package com.innercircle.survey

import com.innercircle.survey.common.IntegrationTest
import com.innercircle.survey.common.jsonMapper
import com.innercircle.survey.controller.request.SurveyCreateRequest
import com.innercircle.survey.controller.request.SurveyQuestionCreateRequest
import com.innercircle.survey.controller.request.SurveyQuestionOptionCreateRequest
import com.innercircle.survey.controller.response.SurveyCreatedResponse
import com.innercircle.survey.enums.SurveyInputType
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@IntegrationTest
class SurveyRestControllerIntegrationTest {

    @Test
    fun testLogPort() {
        println("RestAssured.port = ${RestAssured.port}")
    }

    @Test
    fun `설문을 생성한다`() {
        // given
        val surveyCreateRequest = SurveyCreateRequest(
            name = "설문 이름",
            description = "설문 설명",
            startAt = LocalDateTime.now(),
            endAt = LocalDateTime.now().plusWeeks(1),
            questions = listOf(
                SurveyQuestionCreateRequest(
                    name = "다음중 월클 라인에서 나가야할 대상은?",
                    inputType = SurveyInputType.SINGLE_CHOICE.name,
                    options = listOf(
                        SurveyQuestionOptionCreateRequest(content = "BTS"),
                        SurveyQuestionOptionCreateRequest(content = "봉준호"),
                        SurveyQuestionOptionCreateRequest(content = "손흥민"),
                        SurveyQuestionOptionCreateRequest(content = "제이팍"),
                        SurveyQuestionOptionCreateRequest(content = "레츠고")
                    )
                )
            )
        )
        // when
        val response = RestAssured.given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonMapper().writeValueAsString(surveyCreateRequest))
            .post("/api/surveys")
            .then()
            .statusCode(201)
            .extract()
            .`as`(SurveyCreatedResponse::class.java)

        // then
        assertThat(response.id).isNotNull()
    }
}
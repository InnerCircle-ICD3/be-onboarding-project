package com.innercircle.api.survey.controller

import com.innercircle.api.common.IntegrationTest
import com.innercircle.api.common.jsonMapper
import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.controller.request.SurveyQuestionCreateRequest
import com.innercircle.api.survey.controller.request.SurveyQuestionOptionCreateRequest
import com.innercircle.survey.entity.QuestionType
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.apache.http.HttpHeaders.LOCATION
import org.apache.http.HttpStatus
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@IntegrationTest
class SurveyRestControllerIntegrationTest {

    @Nested
    inner class `설문을 생성한다` {

        private val LOCATION_HEADER_VALUE_REGEX = "/api/surveys/[a-f0-9]{8}-([a-f0-9]{4}-){3}[a-f0-9]{12}"

        @Test
        fun `단일 선택형 설문을 생성한다`() {
            // given
            val surveyCreateRequest = 단일_선택형_설문_생성_요청()

            // when
            val locationHeaderValue = 설문_생성(surveyCreateRequest)

            // then
            assertThat(locationHeaderValue).matches(LOCATION_HEADER_VALUE_REGEX)
        }

        @Test
        fun `다중 선택형 설문을 생성한다`() {
            // given
            val surveyCreateRequest = 다중_선택형_설문_생성_요청()

            // when
            val locationHeaderValue = 설문_생성(surveyCreateRequest)

            // then
            assertThat(locationHeaderValue).matches(LOCATION_HEADER_VALUE_REGEX)
        }

        @Test
        fun `단답형 설문을 생성한다`() {
            // given
            val surveyCreateRequest = 단답형_설문_생성_요청()

            // when
            val locationHeaderValue = 설문_생성(surveyCreateRequest)

            // then
            assertThat(locationHeaderValue).matches(LOCATION_HEADER_VALUE_REGEX)
        }

        @Test
        fun `장문형 설문을 생성한다`() {
            // given
            val surveyCreateRequest = 장문형_설문_생성_요청()

            // when
            val locationHeaderValue = 설문_생성(surveyCreateRequest)

            // then
            assertThat(locationHeaderValue).matches(LOCATION_HEADER_VALUE_REGEX)
        }

    }

    private fun 설문_생성(surveyCreateRequest: SurveyCreateRequest): String? =
        RestAssured.given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(jsonMapper().writeValueAsString(surveyCreateRequest))
            .post("/api/surveys")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .headers()
            .get(LOCATION)
            .value


    private fun 장문형_설문_생성_요청() = SurveyCreateRequest(
        name = "설문 이름",
        description = "설문 설명",
        startAt = LocalDateTime.now(),
        endAt = LocalDateTime.now().plusWeeks(1),
        participantCapacity = 10,
        questions = listOf(
            SurveyQuestionCreateRequest(
                name = "백엔드 개발자를 선택한 이유는?",
                questionType = QuestionType.LONG_ANSWER.name,
                description = "프론트엔드를 하지 않은 이유를 중점으로 논리적으로 서술하시오.",
                required = true
            )
        )
    )

    private fun 단답형_설문_생성_요청() = SurveyCreateRequest(
        name = "설문 이름",
        description = "설문 설명",
        startAt = LocalDateTime.now(),
        endAt = LocalDateTime.now().plusWeeks(1),
        participantCapacity = 10,
        questions = listOf(
            SurveyQuestionCreateRequest(
                name = "첫사랑 이름은?",
                questionType = QuestionType.SHORT_ANSWER.name,
                description = "없으면 어머니 성함을 적으세요",
                required = true
            )
        )
    )

    private fun 다중_선택형_설문_생성_요청() = SurveyCreateRequest(
        name = "설문 이름",
        description = "설문 설명",
        startAt = LocalDateTime.now(),
        endAt = LocalDateTime.now().plusWeeks(1),
        participantCapacity = 10,
        questions = listOf(
            SurveyQuestionCreateRequest(
                name = "다음중 월클 라인에서 나가야할 대상은?",
                questionType = QuestionType.MULTI_CHOICE.name,
                description = "월클 라인에서 나가야할 대상을 선택해주세요.",
                options = listOf(
                    SurveyQuestionOptionCreateRequest(content = "BTS"),
                    SurveyQuestionOptionCreateRequest(content = "봉준호"),
                    SurveyQuestionOptionCreateRequest(content = "손흥민"),
                    SurveyQuestionOptionCreateRequest(content = "제이팍"),
                    SurveyQuestionOptionCreateRequest(content = "레츠고")
                ),
                required = true
            )
        )
    )

    private fun 단일_선택형_설문_생성_요청() = SurveyCreateRequest(
        name = "설문 이름",
        description = "설문 설명",
        startAt = LocalDateTime.now(),
        endAt = LocalDateTime.now().plusWeeks(1),
        participantCapacity = 10,
        questions = listOf(
            SurveyQuestionCreateRequest(
                name = "다음중 월클 라인에서 나가야할 대상은?",
                questionType = QuestionType.SINGLE_CHOICE.name,
                description = "월클 라인에서 나가야할 대상을 선택해주세요.",
                options = listOf(
                    SurveyQuestionOptionCreateRequest(content = "BTS"),
                    SurveyQuestionOptionCreateRequest(content = "봉준호"),
                    SurveyQuestionOptionCreateRequest(content = "손흥민"),
                    SurveyQuestionOptionCreateRequest(content = "제이팍"),
                    SurveyQuestionOptionCreateRequest(content = "레츠고")
                ),
                required = true
            )
        )
    )
}
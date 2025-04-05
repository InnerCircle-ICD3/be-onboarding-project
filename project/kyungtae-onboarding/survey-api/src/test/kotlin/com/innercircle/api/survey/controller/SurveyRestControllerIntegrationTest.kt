package com.innercircle.api.survey.controller

import com.innercircle.api.common.IntegrationTest
import com.innercircle.api.survey.controller.request.SurveyAnswerCreateRequest
import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.controller.request.SurveyUpdateRequest
import com.innercircle.api.survey.controller.response.SurveyResponse
import com.innercircle.survey.entity.QuestionType
import com.innercircle.survey.entity.SurveyStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.temporal.ChronoUnit

@IntegrationTest
class SurveyRestControllerIntegrationTest {

    @Nested
    inner class `설문 질문에 응답한다` {
        @Test
        fun `단일 선택형 설문에 응답한다`() {
            // given
            val surveyCreateRequest = 단일_선택형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val surveyResponse = 설문_조회(surveyId)
            val questionId = surveyResponse.questions?.firstOrNull()?.id
            val questionOptionId = surveyResponse.questions?.firstOrNull()?.options?.firstOrNull()?.id
            val surveyAnswerRequest = 단일_선택형_설문_답변_요청(questionId, questionOptionId, surveyResponse)

            // when
            val locationHeaderValue = 설문_답변(surveyId, surveyAnswerRequest)
            val surveyAnswerId = getIdFromLocation(locationHeaderValue)

            // then
            assertThat(locationHeaderValue).matches(LOCATION_HEADER_VALUE_REGEX)
            assertThat(surveyAnswerId).isNotNull()
        }

        @Test
        fun `다중 선택형 설문에 응답한다`() {
            // given
            val surveyCreateRequest = 다중_선택형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val surveyResponse = 설문_조회(surveyId)
            val questionResponse = surveyResponse.questions?.firstOrNull()
            val questionId = questionResponse?.id
            val questionOptionId = questionResponse?.options?.firstOrNull()?.id!!
            val secondQuestionOptionId = questionResponse.options?.get(1)?.id!!
            val surveyAnswerRequest = 다중_선택형_설문_답변_요청(questionId, surveyResponse, questionOptionId, secondQuestionOptionId)

            // when
            val locationHeaderValue = 설문_답변(surveyId, surveyAnswerRequest)
            val surveyAnswerId = getIdFromLocation(locationHeaderValue)

            // then
            assertThat(locationHeaderValue).matches(LOCATION_HEADER_VALUE_REGEX)
            assertThat(surveyAnswerId).isNotNull()
        }

        @Test
        fun `단답형 설문에 응답한다`() {
            // given
            val surveyCreateRequest = 단답형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val surveyResponse = 설문_조회(surveyId)
            val questionResponse = surveyResponse.questions?.firstOrNull()
            val questionId = questionResponse?.id
            val surveyAnswerRequest = 단답형_설문_답변_요청(questionId, surveyResponse)

            // when
            val locationHeaderValue = 설문_답변(surveyId, surveyAnswerRequest)
            val surveyAnswerId = getIdFromLocation(locationHeaderValue)

            // then
            assertThat(locationHeaderValue).matches(LOCATION_HEADER_VALUE_REGEX)
            assertThat(surveyAnswerId).isNotNull()
        }

        @Test
        fun `장문형 설문에 응답한다`() {
            // given
            val surveyCreateRequest = 장문형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val surveyResponse = 설문_조회(surveyId)
            val questionResponse = surveyResponse.questions?.firstOrNull()
            val questionId = questionResponse?.id
            val surveyAnswerRequest = 장문형_설문_답변_요청(questionId, surveyResponse)

            // when
            val locationHeaderValue = 설문_답변(surveyId, surveyAnswerRequest)
            val surveyAnswerId = getIdFromLocation(locationHeaderValue)

            // then
            assertThat(locationHeaderValue).matches(LOCATION_HEADER_VALUE_REGEX)
            assertThat(surveyAnswerId).isNotNull()
        }
    }


    @Nested
    inner class `설문을_수정한다` {

        @Test
        fun `단일 선택형 설문을 단답형 설문으로 수정한다`() {
            // given
            val surveyCreateRequest = 단일_선택형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val surveyResponse = 설문_조회(surveyId)

            // when
            val surveyUpdateRequest = 단답형_설문_수정_요청(surveyResponse)
            설문_수정(surveyId, surveyUpdateRequest)

            // then
            val modifiedSurveyResponse = 설문_조회(surveyId)
            설문_반환값_검사(modifiedSurveyResponse, surveyId, surveyUpdateRequest)
        }

        @Test
        fun `장문형 설문을 단일 선택형 설문으로 수정한다`() {
            // given
            val surveyCreateRequest = 장문형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val surveyResponse = 설문_조회(surveyId)

            // when
            val surveyUpdateRequest = 단일_선택형_설문_수정_요청(surveyResponse)
            설문_수정(surveyId, surveyUpdateRequest)

            // then
            val modifiedSurveyResponse = 설문_조회(surveyId)
            설문_반환값_검사(modifiedSurveyResponse, surveyId, surveyUpdateRequest)
        }

        @Test
        fun `다중 선택형 설문을 장문형 설문으로 수정한다`() {
            // given
            val surveyCreateRequest = 다중_선택형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val surveyResponse = 설문_조회(surveyId)

            // when
            val surveyUpdateRequest = 장문형_설문_수정_요청(surveyResponse)
            설문_수정(surveyId, surveyUpdateRequest)

            // then
            val modifiedSurveyResponse = 설문_조회(surveyId)
            설문_반환값_검사(modifiedSurveyResponse, surveyId, surveyUpdateRequest)
        }

        @Test
        fun `단답형 설문을 다중 선택형 설문으로 수정한다`() {
            // given
            val surveyCreateRequest = 단답형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val surveyResponse = 설문_조회(surveyId)

            // when
            val surveyUpdateRequest = 다중_선택형_설문_수정_요청(surveyResponse)
            설문_수정(surveyId, surveyUpdateRequest)

            // then
            val modifiedSurveyResponse = 설문_조회(surveyId)
            설문_반환값_검사(modifiedSurveyResponse, surveyId, surveyUpdateRequest)
        }

    }


    @Nested
    inner class `설문을_조회한다` {
        @Test
        fun `단일 선택형 설문을 조회한다`() {
            // given
            val surveyCreateRequest = 단일_선택형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val createdSurveyResponse = 설문_조회(surveyId)
            val questionId = createdSurveyResponse.questions?.firstOrNull()?.id
            val questionOptionId = createdSurveyResponse.questions?.firstOrNull()?.options?.firstOrNull()?.id
            val surveyAnswerRequest = 단일_선택형_설문_답변_요청(questionId, questionOptionId, createdSurveyResponse)
            설문_답변(surveyId, surveyAnswerRequest)
            설문_답변(surveyId, surveyAnswerRequest)
            설문_답변(surveyId, surveyAnswerRequest)

            // when
            val surveyResponse = 설문_조회(surveyId)

            // then
            설문_반환값_검사(surveyResponse, surveyId, surveyCreateRequest, 3)
            설문_질문_반환값_검사(surveyResponse, surveyCreateRequest)
            설문_질문_응답_반환값_검사(surveyResponse, surveyAnswerRequest)
        }


        @Test
        fun `다중 선택형 설문을 조회한다`() {
            // given
            val surveyCreateRequest = 다중_선택형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val createdSurveyResponse = 설문_조회(surveyId)
            val questionId = createdSurveyResponse.questions?.firstOrNull()?.id
            val questionOptionId = createdSurveyResponse.questions?.firstOrNull()?.options?.firstOrNull()?.id!!
            val secondQuestionOptionId = createdSurveyResponse.questions?.firstOrNull()?.options?.get(1)?.id!!
            val surveyAnswerRequest = 다중_선택형_설문_답변_요청(questionId, createdSurveyResponse, questionOptionId, secondQuestionOptionId)
            설문_답변(surveyId, surveyAnswerRequest)
            설문_답변(surveyId, surveyAnswerRequest)
            설문_답변(surveyId, surveyAnswerRequest)

            // when
            val surveyResponse = 설문_조회(surveyId)

            // then
            설문_반환값_검사(surveyResponse, surveyId, surveyCreateRequest, 3)
            설문_질문_반환값_검사(surveyResponse, surveyCreateRequest)
            설문_질문_응답_반환값_검사(surveyResponse, surveyAnswerRequest)
        }

        @Test
        fun `단답형 설문을 조회한다`() {
            // given
            val surveyCreateRequest = 단답형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val createdSurveyResponse = 설문_조회(surveyId)
            val questionId = createdSurveyResponse.questions?.firstOrNull()?.id
            val surveyAnswerRequest = 단답형_설문_답변_요청(questionId, createdSurveyResponse)
            설문_답변(surveyId, surveyAnswerRequest)
            설문_답변(surveyId, surveyAnswerRequest)
            설문_답변(surveyId, surveyAnswerRequest)

            // when
            val surveyResponse = 설문_조회(surveyId)

            // then
            설문_반환값_검사(surveyResponse, surveyId, surveyCreateRequest, 3)
            설문_질문_반환값_검사(surveyResponse, surveyCreateRequest)
            설문_질문_응답_반환값_검사(surveyResponse, surveyAnswerRequest)
        }

        @Test
        fun `장문형 설문을 조회한다`() {
            // given
            val surveyCreateRequest = 장문형_설문_생성_요청()
            val surveyId = getIdFromLocation(설문_생성(surveyCreateRequest))
            val createdSurveyResponse = 설문_조회(surveyId)
            val questionId = createdSurveyResponse.questions?.firstOrNull()?.id
            val surveyAnswerRequest = 장문형_설문_답변_요청(questionId, createdSurveyResponse)
            설문_답변(surveyId, surveyAnswerRequest)
            설문_답변(surveyId, surveyAnswerRequest)
            설문_답변(surveyId, surveyAnswerRequest)

            // when
            val surveyResponse = 설문_조회(surveyId)

            // then
            설문_반환값_검사(surveyResponse, surveyId, surveyCreateRequest, 3)
            설문_질문_반환값_검사(surveyResponse, surveyCreateRequest)
            설문_질문_응답_반환값_검사(surveyResponse, surveyAnswerRequest)
        }

    }

    private fun 설문_질문_응답_반환값_검사(surveyResponse: SurveyResponse, surveyAnswerRequest: SurveyAnswerCreateRequest) {
        val questions = surveyResponse.questions.orEmpty()
        val surveyOptions = questions.flatMap { it.options.orEmpty() }
        questions.forEach {
            if (QuestionType.valueOf(it.questionType!!).isChoiceType()) {
                if (it.questionType == QuestionType.SINGLE_CHOICE.name) {
                    assertThat(surveyAnswerRequest.options).hasSize(1)
                } else {
                    assertThat(surveyAnswerRequest.options).hasSizeGreaterThan(1)
                }
                surveyAnswerRequest.options?.zip(surveyOptions)
                    ?.forEach { (actualOption, expectedOption) ->
                        assertThat(actualOption.content).isEqualTo(expectedOption.content)
                        assertThat(actualOption.optionId).isEqualTo(expectedOption.id)
                    }
            } else {
                assertThat(surveyAnswerRequest.content).isNotBlank()
                assertThat(surveyAnswerRequest.options).isEmpty()
            }
        }

    }

    private fun 설문_질문_반환값_검사(
        surveyResponse: SurveyResponse,
        surveyCreateRequest: SurveyCreateRequest
    ) {
        val questionResponses = surveyResponse.questions.orEmpty()
        val questionRequests = surveyCreateRequest.questions.orEmpty()

        assertThat(questionResponses).hasSize(questionRequests.size)

        questionRequests.zip(questionResponses).forEach { (expected, actual) ->
            assertThat(actual.name).isEqualTo(expected.name)
            assertThat(actual.description).isEqualTo(expected.description)
            assertThat(actual.questionType).isEqualTo(expected.questionType)
            assertThat(actual.required).isEqualTo(expected.required)
            assertThat(actual.createdAt).isNotNull()
            assertThat(actual.updatedAt).isNotNull()

            actual.options?.zip(expected.options.orEmpty())
                ?.forEach { (actualOption, expectedOption) ->
                    assertThat(actualOption.content).isEqualTo(expectedOption.content)
                    assertThat(actualOption.createdAt).isNotNull()
                    assertThat(actualOption.updatedAt).isNotNull()
                }
        }
    }


    private fun 설문_반환값_검사(
        surveyResponse: SurveyResponse,
        surveyId: String,
        surveyCreateRequest: SurveyCreateRequest,
        expectedParticipantCount: Int = 0
    ) {
        assertThat(surveyResponse.externalId.toString()).isEqualTo(surveyId)
        assertThat(surveyResponse.surveyStatus).isEqualTo(SurveyStatus.READY)
        assertThat(surveyResponse.name).isEqualTo(surveyCreateRequest.name)
        assertThat(surveyResponse.description).isEqualTo(surveyCreateRequest.description)
        assertThat(surveyResponse.startAt?.truncatedTo(ChronoUnit.SECONDS))
            .isEqualTo(surveyCreateRequest.startAt?.truncatedTo(ChronoUnit.SECONDS))
        assertThat(surveyResponse.endAt?.truncatedTo(ChronoUnit.SECONDS))
            .isEqualTo(surveyCreateRequest.endAt?.truncatedTo(ChronoUnit.SECONDS))
        assertThat(surveyResponse.participantCapacity).isEqualTo(surveyCreateRequest.participantCapacity)
        assertThat(surveyResponse.participantCount).isEqualTo(expectedParticipantCount)
        assertThat(surveyResponse.createdAt).isNotNull()
        assertThat(surveyResponse.updatedAt).isNotNull()

        if (surveyResponse.questions?.isNotEmpty() == true) {
            assertThat(surveyResponse.questions).hasSize(surveyCreateRequest.questions?.size!!)
            surveyResponse.questions?.forEach { question ->
                val index = surveyResponse.questions!!.indexOf(question)
                val questionUpdateRequest = surveyCreateRequest.questions!![index]
                assertThat(question.name).isEqualTo(questionUpdateRequest.name)
                assertThat(question.description).isEqualTo(questionUpdateRequest.description)
                assertThat(question.questionType).isEqualTo(questionUpdateRequest.questionType)
                assertThat(question.required).isEqualTo(questionUpdateRequest.required)
                assertThat(question.createdAt).isNotNull()
                assertThat(question.updatedAt).isNotNull()

                if (question.questionType == QuestionType.MULTI_CHOICE.name || question.questionType == QuestionType.SINGLE_CHOICE.name) {
                    assertThat(question.options).hasSize(questionUpdateRequest.options?.size ?: 0)
                    question.options?.forEach { option ->
                        val optionIndex = question.options!!.indexOf(option)
                        assertThat(option.content).isEqualTo(questionUpdateRequest.options?.get(optionIndex)?.content ?: "")
                        assertThat(option.createdAt).isNotNull()
                        assertThat(option.updatedAt).isNotNull()
                    }
                } else {
                    assertThat(question.options).isEmpty()
                }
            }
        }
    }

    private fun 설문_반환값_검사(
        surveyResponse: SurveyResponse,
        surveyId: String,
        surveyUpdateRequest: SurveyUpdateRequest
    ) {
        assertThat(surveyResponse.externalId.toString()).isEqualTo(surveyId)
        assertThat(surveyResponse.surveyStatus).isEqualTo(SurveyStatus.READY)
        assertThat(surveyResponse.name).isEqualTo(surveyUpdateRequest.name)
        assertThat(surveyResponse.description).isEqualTo(surveyUpdateRequest.description)
        assertThat(surveyResponse.startAt?.truncatedTo(ChronoUnit.SECONDS))
            .isEqualTo(surveyUpdateRequest.startAt?.truncatedTo(ChronoUnit.SECONDS))
        assertThat(surveyResponse.endAt?.truncatedTo(ChronoUnit.SECONDS))
            .isEqualTo(surveyUpdateRequest.endAt?.truncatedTo(ChronoUnit.SECONDS))
        assertThat(surveyResponse.participantCapacity).isEqualTo(surveyUpdateRequest.participantCapacity)
        assertThat(surveyResponse.createdAt).isNotNull()
        assertThat(surveyResponse.updatedAt).isNotNull()

        if (surveyResponse.questions?.isNotEmpty() == true) {
            assertThat(surveyResponse.questions).hasSize(surveyUpdateRequest.questions.size)
            surveyResponse.questions?.forEach { question ->
                val index = surveyResponse.questions!!.indexOf(question)
                val questionUpdateRequest = surveyUpdateRequest.questions[index]
                assertThat(question.name).isEqualTo(questionUpdateRequest.name)
                assertThat(question.description).isEqualTo(questionUpdateRequest.description)
                assertThat(question.questionType).isEqualTo(questionUpdateRequest.questionType)
                assertThat(question.required).isEqualTo(questionUpdateRequest.required)
                assertThat(question.createdAt).isNotNull()
                assertThat(question.updatedAt).isNotNull()

                if (question.questionType == QuestionType.MULTI_CHOICE.name || question.questionType == QuestionType.SINGLE_CHOICE.name) {
                    assertThat(question.options).hasSize(questionUpdateRequest.options?.size ?: 0)
                    question.options?.forEach { option ->
                        val optionIndex = question.options!!.indexOf(option)
                        assertThat(option.content).isEqualTo(questionUpdateRequest.options?.get(optionIndex)?.content ?: "")
                        assertThat(option.createdAt).isNotNull()
                        assertThat(option.updatedAt).isNotNull()
                    }
                } else {
                    assertThat(question.options).isEmpty()
                }
            }
        }

    }

    @Nested
    inner class `설문을 생성한다` {

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

}
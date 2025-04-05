package org.survey.taehwanonboarding.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.survey.taehwanonboarding.api.dto.ResponseItemRequest
import org.survey.taehwanonboarding.api.dto.ResponseSubmitRequest
import org.survey.taehwanonboarding.api.dto.ResponseSubmitResponse
import org.survey.taehwanonboarding.domain.entity.response.SurveyResponse
import org.survey.taehwanonboarding.domain.entity.response.SurveyResponseItem
import org.survey.taehwanonboarding.domain.entity.survey.Survey
import org.survey.taehwanonboarding.domain.repository.survey.SurveyRepository
import org.survey.taehwanonboarding.domain.repository.survey.SurveyResponseRepository
import java.time.format.DateTimeFormatter

@Service
class ResponseService(
    private val surveyRepository: SurveyRepository,
    private val responseRepository: SurveyResponseRepository,
) {
    @Transactional
    fun submitResponse(surveyId: Long, request: ResponseSubmitRequest): ResponseSubmitResponse {
        // 설문조사 조회
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 설문조사입니다: ID=$surveyId") }

        // 설문이 활성 상태인지 확인
        require(survey.status == Survey.SurveyStatus.ACTIVE) {
            "활성 상태의 설문조사만 응답할 수 있습니다. 현재 상태: ${survey.status}"
        }

        // 응답 항목 유효성 검사
        validateResponseItems(survey, request.items)

        // 응답 생성
        val response = SurveyResponse(
            survey = survey,
            respondentId = request.respondentId,
            status = SurveyResponse.ResponseStatus.SUBMITTED,
        )

        // 응답 항목 추가
        request.items.forEach { itemRequest ->
            val surveyItem = survey.items.find { it.id == itemRequest.itemId }
                ?: throw IllegalArgumentException("존재하지 않는 질문 항목입니다: ID=${itemRequest.itemId}")

            val responseItem = SurveyResponseItem(
                surveyItem = surveyItem,
                value = itemRequest.value,
            )

            // 응답값 유효성 검증
            require(responseItem.isValid()) {
                "유효하지 않은 응답값입니다: itemId=${itemRequest.itemId}, value=${itemRequest.value}"
            }

            response.addResponseItem(responseItem)
        }

        val savedResponse = responseRepository.save(response)

        return ResponseSubmitResponse(
            id = savedResponse.id!!,
            surveyId = survey.id!!,
            status = savedResponse.status.name,
            responseAt = savedResponse.responseAt?.format(DateTimeFormatter.ISO_DATE_TIME) ?: "",
        )
    }

    private fun validateResponseItems(survey: Survey, items: List<ResponseItemRequest>) {
        val requiredItemIds = survey.items
            .filter { it.required }
            .mapNotNull { it.id }
            .toSet()

        val submittedItemIds = items.map { it.itemId }.toSet()

        val missingRequiredItems = requiredItemIds - submittedItemIds
        require(missingRequiredItems.isEmpty()) {
            "필수 응답 항목이 누락되었습니다: $missingRequiredItems"
        }

        val validItemIds = survey.items.mapNotNull { it.id }.toSet()
        val invalidItemIds = submittedItemIds - validItemIds
        require(invalidItemIds.isEmpty()) {
            "존재하지 않는 질문 항목이 포함되었습니다: $invalidItemIds"
        }
    }
}
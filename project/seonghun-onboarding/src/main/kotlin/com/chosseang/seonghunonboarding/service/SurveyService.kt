package com.chosseang.seonghunonboarding.service

import com.chosseang.seonghunonboarding.dto.SurveyCreateRequest
import com.chosseang.seonghunonboarding.dto.SurveyCreateResponse
import com.chosseang.seonghunonboarding.entity.Item
import com.chosseang.seonghunonboarding.entity.Survey
import com.chosseang.seonghunonboarding.enum.ItemType
import com.chosseang.seonghunonboarding.repository.SurveyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SurveyService(val repository: SurveyRepository) {

    @Transactional
    fun createSurvey(surveyCreateRequest: SurveyCreateRequest): SurveyCreateResponse {

        val survey = Survey(
            name = surveyCreateRequest.name,
            description = surveyCreateRequest.description,
            items = mutableListOf() // 빈 리스트로 시작
        )

        // Survey 엔티티에 구현된 메서드를 사용하여 아이템 추가
        survey.addItems(surveyCreateRequest.items)

        // 저장 후 응답 변환
        return repository.save(survey).let {
            SurveyCreateResponse(
                name = it.name,
                description = it.description,
                items = it.items.toMutableList()
            )
        }
    }

    fun searchSurvey(): List<Survey> {
        return repository.findAll()
    }

    @Transactional
    fun updateSurvey(survey: Survey): Survey? {
        val findSurvey = repository.findByIdOrNull(survey.id!!)

        if (survey.name.isNotBlank()) {
            findSurvey?.name = survey.name
        }

        if (survey.description.isNotBlank()) {
            findSurvey?.description = survey.description
        }

        survey.items?.let { itemRequests ->
            // 기존 아이템들 맵으로 변환
            val existingItemsMap = findSurvey?.items?.associateBy { it.id }

            // 요청에 없는 아이템 모두 제거 (orphanRemoval이 처리함)
            val requestItemIds = itemRequests.mapNotNull { it.id }.toSet()
            findSurvey?.items?.removeIf { it.id != null && it.id !in requestItemIds }

            // 업데이트하거나 새로 추가
            itemRequests.forEach { itemRequest ->
                if (existingItemsMap != null) {
                    if (itemRequest.id != null && existingItemsMap.containsKey(itemRequest.id)) {
                        // 기존 아이템 업데이트
                        val item = existingItemsMap[itemRequest.id]!!
                        itemRequest.name?.let { item.name = it }
                        itemRequest.description?.let { item.description = it }
                        itemRequest.type?.let { /* type은 val이라 업데이트 불가 */ }
                        itemRequest.contents?.let { item.contents = it }
                    } else {
                        // 새 아이템 추가
                        val newItem = Item(
                            id = null,
                            name = itemRequest.name ?: "",
                            description = itemRequest.description ?: "",
                            type = itemRequest.type ?: ItemType.ShortAnswer,
                            contents = itemRequest.contents ?: emptyList(),
                            survey = findSurvey
                        )
                        findSurvey?.addItem(newItem)
                    }
                }
            }
        }
        return findSurvey
    }
}

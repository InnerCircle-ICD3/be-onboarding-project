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

        // 아이템 업데이트
        survey.items.forEach { requestItem ->
            if (requestItem.id != null) {
                // 기존 아이템 찾기
                val existingItem = findSurvey?.items?.find { it.id == requestItem.id }

                if (existingItem != null) {
                    // 기존 아이템 업데이트 (item 클래스의 필드가 var인 경우)
                    if (requestItem.name.isNotBlank()) {
                        existingItem.name = requestItem.name
                    }
                    if (requestItem.description.isNotBlank()) {
                        existingItem.description = requestItem.description
                    }
                    if (requestItem.contents.isNotEmpty()) {
                        existingItem.contents = requestItem.contents
                    }
                }
            } else {
                // 새 아이템 추가
                val newItem = Item(
                    id = null,
                    name = requestItem.name,
                    description = requestItem.description,
                    type = requestItem.type,
                    contents = requestItem.contents,
                    survey = findSurvey
                )
                findSurvey?.addItem(newItem)
            }
        }
        return findSurvey
    }
}

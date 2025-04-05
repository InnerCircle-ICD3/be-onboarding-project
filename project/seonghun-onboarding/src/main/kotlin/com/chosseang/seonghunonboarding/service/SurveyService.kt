package com.chosseang.seonghunonboarding.service

import com.chosseang.seonghunonboarding.dto.SurveyCreateRequest
import com.chosseang.seonghunonboarding.dto.SurveyCreateResponse
import com.chosseang.seonghunonboarding.entity.Survey
import com.chosseang.seonghunonboarding.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SurveyService(val repository: SurveyRepository) {

    @Transactional
    fun createSurvey(surveyCreateRequest: SurveyCreateRequest): SurveyCreateResponse {
        // 각 아이템의 survey 참조 설정 (양방향 관계 유지)
        surveyCreateRequest.items.forEach { item ->
            item.survey = Survey(
                name = surveyCreateRequest.name,
                description = surveyCreateRequest.description,
                items = surveyCreateRequest.items.toMutableList()
            )
        }

        return repository.save(Survey(
            name = surveyCreateRequest.name,
            description = surveyCreateRequest.description,
            items = surveyCreateRequest.items.toMutableList()
        )).let {
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
}

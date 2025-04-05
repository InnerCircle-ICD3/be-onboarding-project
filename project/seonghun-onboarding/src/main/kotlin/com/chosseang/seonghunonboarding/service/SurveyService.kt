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
}

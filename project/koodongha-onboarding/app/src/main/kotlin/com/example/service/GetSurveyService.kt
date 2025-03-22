package com.example.service

import com.example.dto.SurveyDetailResponse
import com.example.dto.SurveyItemResponse
import com.example.entity.InputType
import com.example.repository.SurveyRepository
import com.springframework.stereotype.Service

@Service
class GetSurveyService(
    private val surveyRepository: SurveyRepository
) {
    fun getSurvey(id: Long): SurveyDetailResponse {
        val survey = surveyRepository.findById(id).orElseThrow { RuntimeException("설문 없음!") }

        return SurveyDetailResponse(
            id = survey.id,
            title = survey.title,
            description = survey.description,
            items = survey.items.map { item ->
                SurveyItemResponse(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    inputType = item.inputType,
                    isRequired = item.isRequired,
                    options = item.options.map { it.value }
                )
            }
        )
    }
}

package com.example.service

import com.example.dto.CreateSurveyRequest
import com.example.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class CreateSurveyService(
    private val surveyRepository: SurveyRepository
) {
    fun createSurvey(request: CreateSurveyRequest) {
        // TODO: 구현 예정
    }
}

package com.innercircle.api.survey.service

import com.innercircle.domain.survey.command.service.SurveyCommandService
import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.controller.response.SurveyCreatedResponse
import org.springframework.stereotype.Service

@Service
class SurveyService(
    private val surveyCommandService: SurveyCommandService
) {
    fun createSurvey(request: SurveyCreateRequest): SurveyCreatedResponse {
        return surveyCommandService.create(request.toCommand()).let { SurveyCreatedResponse(it.id) }
    }
}
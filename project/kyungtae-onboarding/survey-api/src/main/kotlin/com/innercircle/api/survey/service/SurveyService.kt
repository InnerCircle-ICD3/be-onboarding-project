package com.innercircle.api.survey.service

import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.controller.request.SurveyUpdateRequest
import com.innercircle.api.survey.controller.response.SurveyCreatedResponse
import com.innercircle.api.survey.controller.response.SurveyResponse
import com.innercircle.domain.survey.command.service.SurveyCommandService
import com.innercircle.domain.survey.query.service.SurveyQueryService
import org.springframework.stereotype.Service
import java.util.*

@Service
class SurveyService(
    private val surveyCommandService: SurveyCommandService,
    private val surveyQueryService: SurveyQueryService
) {
    fun createSurvey(request: SurveyCreateRequest): SurveyCreatedResponse {
        val survey = surveyCommandService.create(request.toCommand())

        return SurveyCreatedResponse(survey.externalId)
    }

    fun getSurvey(id: UUID): SurveyResponse {
        val survey = surveyQueryService.fetchSurveyAggregateOrThrow(id)
        return SurveyResponse.from(survey)
    }

    fun updateSurvey(id: UUID, request: SurveyUpdateRequest) {
        surveyCommandService.update(id, request.toCommand(id))
    }
}
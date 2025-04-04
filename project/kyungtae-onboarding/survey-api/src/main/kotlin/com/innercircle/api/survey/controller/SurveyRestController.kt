package com.innercircle.api.survey.controller

import com.innercircle.api.common.response.ApiResponse
import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.controller.request.SurveyUpdateRequest
import com.innercircle.api.survey.controller.response.SurveyResponse
import com.innercircle.api.survey.service.SurveyService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RequestMapping("/api/surveys")
@RestController
class SurveyRestController(
    private val surveyService: SurveyService
) {

    @PostMapping
    fun createSurvey(
        @RequestBody @Valid request: SurveyCreateRequest
    ): ResponseEntity<Unit> {
        val surveyCreatedResponse = surveyService.createSurvey(request)
        return ResponseEntity.created(URI.create("/api/surveys/${surveyCreatedResponse.externalId}")).build()
    }

    @GetMapping("/{id:[a-f0-9]{8}-(?:[a-f0-9]{4}-){3}[a-f0-9]{12}}")
    fun getSurvey(
        @PathVariable id: UUID
    ): ResponseEntity<ApiResponse<SurveyResponse>> {
        val survey = surveyService.getSurvey(id)
        return ResponseEntity.ok(ApiResponse.ok(survey))
    }

    @PutMapping("/{id:[a-f0-9]{8}-(?:[a-f0-9]{4}-){3}[a-f0-9]{12}}")
    fun updateSurvey(
        @PathVariable id: UUID,
        @RequestBody @Valid request: SurveyUpdateRequest
    ): ResponseEntity<Unit> {
        surveyService.updateSurvey(id, request)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id:[a-f0-9]{8}-(?:[a-f0-9]{4}-){3}[a-f0-9]{12}}/answers")
    fun answerSurvey(
        @RequestBody @Valid request: SurveyCreateRequest
    ): ResponseEntity<Unit> {
        val surveyCreatedResponse = surveyService.createSurvey(request)
        return ResponseEntity.created(URI.create("/api/surveys/${surveyCreatedResponse.externalId}")).build()
    }
}
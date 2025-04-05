package org.survey.taehwanonboarding.application.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.survey.taehwanonboarding.api.dto.SurveyCreateRequest
import org.survey.taehwanonboarding.api.dto.SurveyCreateResponse
import org.survey.taehwanonboarding.api.dto.SurveyDetailResponse
import org.survey.taehwanonboarding.api.dto.SurveySummaryResponse
import org.survey.taehwanonboarding.application.service.SurveyService
import org.survey.taehwanonboarding.domain.entity.survey.Survey

@RestController
@RequestMapping("/api/v1/survey")
class SurveyController(
    private val surveyService: SurveyService,
) {
    @PostMapping
    fun createSurvey(
        @RequestBody request: SurveyCreateRequest,
    ): ResponseEntity<SurveyCreateResponse> {
        val response = surveyService.createSurvey(request)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping
    fun getSurveys(
        @RequestParam(required = false) status: String?,
    ): ResponseEntity<List<SurveySummaryResponse>> {
        // todo: 공통 RequestParam 검증 로직으로 분리 가능
        val surveyStatus = status?.let {
            try {
                Survey.SurveyStatus.valueOf(it.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("유효하지 않은 상태값입니다: $status")
            }
        }

        val response = surveyService.getSurveys(surveyStatus)
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getSurveyById(
        @PathVariable id: Long,
    ): ResponseEntity<SurveyDetailResponse> {
        val response = surveyService.getSurveyById(id)
        return ResponseEntity(response, HttpStatus.OK)
    }
}
package org.survey.taehwanonboarding.application.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.survey.taehwanonboarding.api.dto.SurveyCreateRequest
import org.survey.taehwanonboarding.api.dto.SurveyCreateResponse
import org.survey.taehwanonboarding.application.service.SurveyService

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
}
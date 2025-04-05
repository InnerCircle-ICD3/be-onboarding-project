package org.survey.taehwanonboarding.application.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.survey.taehwanonboarding.api.dto.ResponseSubmitRequest
import org.survey.taehwanonboarding.api.dto.ResponseSubmitResponse
import org.survey.taehwanonboarding.application.service.ResponseService

@RestController
@RequestMapping("/api/v1/survey/{surveyId}/responses")
class ResponseController(
    private val responseService: ResponseService,
) {
    @PostMapping
    fun submitResponse(
        @PathVariable surveyId: Long,
        @RequestBody request: ResponseSubmitRequest,
    ): ResponseEntity<ResponseSubmitResponse> {
        val response = responseService.submitResponse(surveyId, request)
        return ResponseEntity(response, HttpStatus.CREATED)
    }
}
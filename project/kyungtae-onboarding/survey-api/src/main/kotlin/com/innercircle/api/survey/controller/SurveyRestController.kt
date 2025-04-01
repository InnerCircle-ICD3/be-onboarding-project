package com.innercircle.api.survey.controller

import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.service.SurveyService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

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
        return ResponseEntity.created(URI.create("/api/surveys/${surveyCreatedResponse.id}")).build()

    }

}
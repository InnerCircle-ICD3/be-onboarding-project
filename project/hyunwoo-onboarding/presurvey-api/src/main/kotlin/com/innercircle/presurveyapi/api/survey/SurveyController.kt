package com.innercircle.presurveyapi.api.survey

import com.innercircle.presurveyapi.api.survey.dto.CreateSurveyRequest
import com.innercircle.presurveyapi.api.survey.dto.SurveyResponse
import com.innercircle.presurveyapi.application.survey.command.CreateSurveyUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/surveys")
class SurveyController(
    private val createSurveyUseCase: CreateSurveyUseCase
) {

    @PostMapping
    fun createSurvey(@RequestBody request: CreateSurveyRequest): ResponseEntity<SurveyResponse> {
        val command = request.toCommand()
        val survey = createSurveyUseCase.invoke(command)
        return ResponseEntity.ok(SurveyResponse.from(survey))
    }
}
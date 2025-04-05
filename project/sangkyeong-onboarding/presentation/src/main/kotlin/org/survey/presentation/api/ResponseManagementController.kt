package org.survey.presentation.api

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.survey.application.dto.response.SurveyAnswerResponse
import org.survey.application.usecase.response.GetSurveyResponsesUseCase
import org.survey.application.usecase.response.SubmitSurveyResponseUseCase
import org.survey.presentation.dto.request.CreateSurveyResponseRequest
import org.survey.presentation.dto.request.toCommand

@RestController
@RequestMapping("/api/v1/surveys")
class ResponseManagementController(
    private val submitSurveyResponseUseCase: SubmitSurveyResponseUseCase,
    private val getSurveyResponsesUseCase: GetSurveyResponsesUseCase,
) {
    @PostMapping("/{surveyId}/responses")
    @ResponseStatus(HttpStatus.CREATED)
    fun createSurveyResponse(
        @PathVariable surveyId: Long,
        @RequestBody request: CreateSurveyResponseRequest,
    ) {
        val createSurveyResponseCommand = request.toCommand()
        submitSurveyResponseUseCase.execute(surveyId, createSurveyResponseCommand)
    }

    @GetMapping("/{surveyId}/responses")
    fun getSurveyResponses(
        @PathVariable surveyId: Long,
    ): List<SurveyAnswerResponse> {
        return getSurveyResponsesUseCase.execute(surveyId)
    }
}

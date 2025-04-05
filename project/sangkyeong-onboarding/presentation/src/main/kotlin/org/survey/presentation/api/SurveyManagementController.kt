package org.survey.presentation.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.survey.application.dto.response.SurveyResponse
import org.survey.application.usecase.survey.CreateSurveyUseCase
import org.survey.application.usecase.survey.GetSurveyUseCase
import org.survey.application.usecase.survey.UpdateSurveyUseCase
import org.survey.presentation.dto.request.CreateSurveyRequest
import org.survey.presentation.dto.request.UpdateSurveyRequest
import org.survey.presentation.dto.request.toCommand

@RestController
@RequestMapping("/api/v1/surveys")
class SurveyManagementController(
    private val createSurveyUseCase: CreateSurveyUseCase,
    private val getSurveyUseCase: GetSurveyUseCase,
    private val updateSurveyUseCase: UpdateSurveyUseCase,
) {
    @PostMapping
    fun createSurvey(
        @RequestBody request: CreateSurveyRequest,
    ) {
        val createSurveyCommand = request.toCommand()
        createSurveyUseCase.execute(createSurveyCommand)
    }

    @GetMapping("/{surveyId}")
    fun getSurvey(
        @PathVariable("surveyId") surveyId: Long,
    ): SurveyResponse {
        return getSurveyUseCase.execute(surveyId)
    }

    @PatchMapping("/{surveyId}")
    fun updateSurvey(
        @PathVariable("surveyId") surveyId: Long,
        @RequestBody request: UpdateSurveyRequest,
    ) {
        val updateSurveyCommand = request.toCommand()
        updateSurveyUseCase.execute(surveyId, updateSurveyCommand)
    }
}

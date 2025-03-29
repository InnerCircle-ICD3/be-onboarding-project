package org.survey.presentation.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.survey.application.usecase.CreateSurveyUseCase
import org.survey.presentation.dto.request.CreateSurveyRequest
import org.survey.presentation.dto.request.toCommand

@RestController
@RequestMapping("/api/v1/surveys")
class SurveyManagementController(
    private val createSurveyUseCase: CreateSurveyUseCase,
) {
    @PostMapping
    fun createSurvey(
        @RequestBody request: CreateSurveyRequest,
    ) {
        val createSurveyCommand = request.toCommand()
        createSurveyUseCase.execute(createSurveyCommand)
    }
}

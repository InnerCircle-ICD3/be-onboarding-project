package org.survey.application.usecase.response

import org.springframework.stereotype.Service
import org.survey.application.dto.command.CreateSurveyResponseCommand
import org.survey.application.dto.command.toDomain
import org.survey.domain.response.service.ResponseDomainService

@Service
class SubmitSurveyResponseUseCase(
    private val responseDomainService: ResponseDomainService,
) {
    fun execute(
        surveyId: Long,
        command: CreateSurveyResponseCommand,
    ) {
        val request = command.toDomain()

        responseDomainService.submitSurveyResponse(surveyId, request)
    }
}

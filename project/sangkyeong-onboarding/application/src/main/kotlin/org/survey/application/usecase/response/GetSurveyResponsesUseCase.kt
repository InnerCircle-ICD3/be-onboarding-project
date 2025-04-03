package org.survey.application.usecase.response

import org.springframework.stereotype.Service
import org.survey.application.dto.response.ChoiceAnswerResponse
import org.survey.application.dto.response.SurveyAnswerResponse
import org.survey.application.dto.response.TextAnswerResponse
import org.survey.domain.response.model.ChoiceAnswer
import org.survey.domain.response.model.TextAnswer
import org.survey.domain.response.service.ResponseDomainService
import org.survey.domain.survey.service.SurveyCommandService

@Service
class GetSurveyResponsesUseCase(
    private val responseDomainService: ResponseDomainService,
    private val surveyCommandService: SurveyCommandService,
) {
    fun execute(surveyId: Long): List<SurveyAnswerResponse> {
        val responses = responseDomainService.getSurveyResponses(surveyId)

        return responses.map { surveyResponse ->
            SurveyAnswerResponse(
                id = surveyResponse.id,
                surveyId = surveyResponse.surveyId,
                createdAt = surveyResponse.createdAt,
                answers =
                    surveyResponse.answers.map { answer ->
                        when (answer) {
                            is TextAnswer ->
                                TextAnswerResponse(
                                    surveyItemId = answer.surveyItemId,
                                    value = answer.value,
                                )
                            is ChoiceAnswer ->
                                ChoiceAnswerResponse(
                                    surveyItemId = answer.surveyItemId,
                                    itemOptionIds = answer.itemOptionIds,
                                )
                        }
                    },
            )
        }
    }
}

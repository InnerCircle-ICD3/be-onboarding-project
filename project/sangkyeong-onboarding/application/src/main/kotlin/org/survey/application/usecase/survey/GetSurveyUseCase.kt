package org.survey.application.usecase.survey

import org.springframework.stereotype.Service
import org.survey.application.dto.response.ItemOptionResponse
import org.survey.application.dto.response.SurveyItemResponse
import org.survey.application.dto.response.SurveyResponse
import org.survey.domain.survey.service.SurveyDomainService

@Service
class GetSurveyUseCase(
    private val surveyDomainService: SurveyDomainService,
) {
    fun execute(surveyId: Long): SurveyResponse {
        val survey = surveyDomainService.getSurvey(surveyId)

        val surveyItems = surveyDomainService.getSurveyItems(surveyId)

        val itemOptions = surveyDomainService.getItemOptions(surveyItems.map { it.id })

        return SurveyResponse(
            id = survey.id,
            title = survey.title,
            description = survey.description,
            createdAt = survey.createdAt,
            updatedAt = survey.updatedAt,
            options =
                surveyItems.map { item ->
                    SurveyItemResponse(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        inputType = item.inputType,
                        isRequired = item.isRequired,
                        options =
                            itemOptions
                                .filter { it.surveyItemId == item.id }
                                .map { ItemOptionResponse(it.id, it.surveyItemId, it.value) },
                    )
                },
        )
    }
}

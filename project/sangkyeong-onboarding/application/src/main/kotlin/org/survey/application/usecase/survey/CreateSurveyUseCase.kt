package org.survey.application.usecase.survey

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.survey.application.dto.command.CreateSurveyCommand
import org.survey.domain.dto.SurveyItemData
import org.survey.domain.survey.service.SurveyDomainService

@Service
class CreateSurveyUseCase(
    private val surveyDomainService: SurveyDomainService,
) {
    @Transactional
    fun execute(command: CreateSurveyCommand) {
        val surveyItemDataList =
            command.items.map { itemCommand ->
                SurveyItemData(
                    title = itemCommand.title,
                    description = itemCommand.description,
                    inputType = itemCommand.inputType,
                    isRequired = itemCommand.isRequired,
                    options = itemCommand.options?.map { it.value },
                )
            }

        surveyDomainService.createSurvey(command.title, command.description, surveyItemDataList)
    }
}

package com.example.service

import com.example.dto.CreateSurveyRequest
import com.example.entity.*
import com.example.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class CreateSurveyService(
    private val surveyRepository: SurveyRepository
) {
    fun createSurvey(request: CreateSurveyRequest): Long {
        val survey = Survey(
            title = request.title,
            description = request.description.orEmpty()
        )

        val items = request.items.map { itemReq ->
            val item = SurveyItem(
                name = itemReq.name,
                description = itemReq.description,
                inputType = itemReq.inputType,
                isRequired = itemReq.isRequired,
                survey = survey
            )

            if (itemReq.inputType in listOf(InputType.SINGLE_CHOICE, InputType.MULTI_CHOICE)) {
                item.options.addAll(
                    itemReq.options?.map { SelectionOption(value = it, surveyItem = item) } ?: emptyList()
                )
            }

            item
        }

        survey.items.addAll(items)
        return surveyRepository.save(survey).id
    }
}
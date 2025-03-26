package com.example.service

import com.example.dto.CreateSurveyRequest
import com.example.entity.*
import com.example.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class CreateSurveyService(
    private val surveyRepository: SurveyRepository
) {

    fun createSurvey(request: CreateSurveyRequest) {
        validate(request)

        val survey = Survey(
            title = request.title,
            description = request.description
        )

        request.items.forEach { itemRequest ->
            val item = SurveyItem(
                name = itemRequest.name,
                description = itemRequest.description,
                inputType = itemRequest.inputType,
                isRequired = itemRequest.isRequired,
                survey = survey
            )

            if (item.inputType.isChoice()) {
                itemRequest.options?.forEach { option ->
                    val selectionOption = SelectionOption(
                        value = option,
                        surveyItem = item
                    )
                    item.options.add(selectionOption)
                }
            }

            survey.items.add(item)
        }

        surveyRepository.save(survey)
    }

    private fun validate(request: CreateSurveyRequest) {
        if (request.title.isBlank()) {
            throw IllegalArgumentException("Survey title is required.")
        }

        if (request.description.isNullOrBlank()) {
            throw IllegalArgumentException("Survey description is required.")
        }

        if (request.items.isEmpty() || request.items.size > 10) {
            throw IllegalArgumentException("Survey items must be between 1 and 10.")
        }

        request.items.forEach { item ->
            if (item.name.isBlank()) {
                throw IllegalArgumentException("Item name is required.")
            }

            if (item.description.isNullOrBlank()) {
                throw IllegalArgumentException("Item description is required.")
            }

            if (item.inputType.isChoice() && item.options.isNullOrEmpty()) {
                throw IllegalArgumentException("Choice-type items must have options.")
            }
        }
    }

    private fun InputType.isChoice(): Boolean =
        this == InputType.SINGLE_CHOICE || this == InputType.MULTI_CHOICE
}

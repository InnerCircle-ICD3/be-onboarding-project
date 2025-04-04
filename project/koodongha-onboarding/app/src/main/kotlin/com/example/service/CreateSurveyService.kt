package com.example.service

import com.example.dto.*
import com.example.entity.*
import com.example.repository.SurveyRepository
import com.example.exception.InvalidSurveyRequestException
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
            val item = when (itemRequest) {
                is TextItemRequest -> TextItem(
                    isLong = itemRequest.isLong,
                    name = itemRequest.name,
                    description = itemRequest.description,
                    isRequired = itemRequest.isRequired,
                    survey = survey
                )
                is ChoiceItemRequest -> {
                    val choiceItem = ChoiceItem(
                        isMultiple = itemRequest.isMultiple,
                        name = itemRequest.name,
                        description = itemRequest.description,
                        isRequired = itemRequest.isRequired,
                        survey = survey
                    )
                    itemRequest.options.forEach { option ->
                        val selectionOption = SelectionOption(
                            value = option,
                            item = choiceItem
                        )
                        choiceItem.options.add(selectionOption)
                    }
                    choiceItem
                }
            }
            survey.items.add(item)
        }

        surveyRepository.save(survey)
    }

    private fun validate(request: CreateSurveyRequest) {
        if (request.title.isBlank()) {
            throw InvalidSurveyRequestException("Survey title is required.")
        }

        if (request.description.isNullOrBlank()) {
            throw InvalidSurveyRequestException("Survey description is required.")
        }

        if (request.items.isEmpty() || request.items.size > 10) {
            throw InvalidSurveyRequestException("Survey items must be between 1 and 10.")
        }

        request.items.forEach { item ->
            if (item.name.isBlank()) {
                throw InvalidSurveyRequestException("Item name is required.")
            }

            if (item.description.isNullOrBlank()) {
                throw InvalidSurveyRequestException("Item description is required.")
            }

            if (item is ChoiceItemRequest && item.options.isEmpty()) {
                throw InvalidSurveyRequestException("Choice-type items must have options.")
            }
        }
    }
}
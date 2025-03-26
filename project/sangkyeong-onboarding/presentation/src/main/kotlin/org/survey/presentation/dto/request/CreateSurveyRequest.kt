package org.survey.presentation.dto.request

import org.survey.application.dto.CreateItemOptionCommand
import org.survey.application.dto.CreateSurveyCommand
import org.survey.application.dto.CreateSurveyItemCommand

data class CreateSurveyRequest(
    val title: String,
    val description: String,
    val items: List<CreateSurveyItemRequest>,
) {
    init {
        require(items.size in 1..10) {
            "설문 항목은 최소 1개, 최대 10개까지 추가할 수 있습니다."
        }
    }
}

data class CreateSurveyItemRequest(
    val title: String,
    val description: String,
    val inputType: String,
    val isRequired: Boolean,
    val options: List<CreateItemOptionRequest>? = null,
) {
    init {
        require(
            inputType.contentEquals("SHORT_TEXT") ||
                inputType.contentEquals("LONG_TEXT") ||
                inputType.contentEquals("SINGLE_CHOICE") ||
                inputType.contentEquals("MULTIPLE_CHOICE"),
        ) {
            "inputType은 SHORT_TEXT, LONG_TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE 중 하나여야 합니다."
        }
    }
}

data class CreateItemOptionRequest(
    val value: String,
)

fun CreateSurveyRequest.toCommand() =
    CreateSurveyCommand(
        title = title,
        description = description,
        items = items.map { it.toCommand() },
    )

private fun CreateSurveyItemRequest.toCommand() =
    CreateSurveyItemCommand(
        title = title,
        description = description,
        inputType = inputType,
        isRequired = isRequired,
        options = options?.map { it.toCommand() },
    )

private fun CreateItemOptionRequest.toCommand() = CreateItemOptionCommand(value = value)

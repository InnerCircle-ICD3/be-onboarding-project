package org.survey.presentation.dto.request

import org.survey.application.dto.command.UpdateItemOptionCommand
import org.survey.application.dto.command.UpdateSurveyCommand
import org.survey.application.dto.command.UpdateSurveyItemCommand

data class UpdateSurveyRequest(
    val title: String,
    val description: String,
    val items: List<UpdateSurveyItemRequest>,
)

data class UpdateSurveyItemRequest(
    val id: Long,
    val title: String,
    val description: String,
    val inputType: String,
    val isRequired: Boolean,
    val options: List<UpdateItemOptionRequest>? = null,
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

data class UpdateItemOptionRequest(
    val id: Long,
    val value: String,
)

fun UpdateSurveyRequest.toCommand() =
    UpdateSurveyCommand(
        title = title,
        description = description,
        items = items.map { it.toCommand() },
    )

private fun UpdateSurveyItemRequest.toCommand() =
    UpdateSurveyItemCommand(
        id = id,
        title = title,
        description = description,
        inputType = inputType,
        isRequired = isRequired,
        options = options?.map { it.toCommand() },
    )

private fun UpdateItemOptionRequest.toCommand() = UpdateItemOptionCommand(id = id, value = value)

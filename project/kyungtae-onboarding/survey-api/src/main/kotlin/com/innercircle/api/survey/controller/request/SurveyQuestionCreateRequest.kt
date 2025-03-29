package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyQuestionCreateCommand
import com.innercircle.survey.entity.QuestionType

data class SurveyQuestionCreateRequest(
    val name: String? = null,
    val description: String? = null,
    val inputType: String? = null,
    val required: Boolean? = null,
    val options: List<SurveyQuestionOptionCreateRequest>? = emptyList()
) {
    fun toCommand(): SurveyQuestionCreateCommand {
        return SurveyQuestionCreateCommand(
            name = name!!,
            description = description!!,
            inputType = QuestionType.valueOf(inputType!!),
            required = required!!,
            options = options?.map { it.toCommand() }?.toList() ?: emptyList()
        )
    }
}

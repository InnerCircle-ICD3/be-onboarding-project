package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyQuestionUpdateCommand
import com.innercircle.survey.entity.QuestionType

data class SurveyQuestionUpdateRequest(
    val id: Long? = null,
    val name: String? = null,
    val questionType: String? = null,
    val description: String? = null,
    val options: List<SurveyQuestionOptionUpdateRequest>? = emptyList(),
    val required: Boolean? = false
) {
    fun toCommand(): SurveyQuestionUpdateCommand = SurveyQuestionUpdateCommand(
        id = id!!,
        name = name!!,
        questionType = QuestionType.valueOf(questionType!!),
        description = description!!,
        options = options?.map { it.toCommand() } ?: emptyList(),
        required = required!!
    )
}

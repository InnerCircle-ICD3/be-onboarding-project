package com.innercircle.domain.survey.command.dto

import com.innercircle.survey.entity.QuestionType

data class SurveyQuestionUpdateCommand(
    val id: Long?,
    val name: String,
    val description: String,
    val questionType: QuestionType,
    val required: Boolean,
    val options: List<SurveyQuestionOptionUpdateCommand>
) {
    fun toCreateCommand(): SurveyQuestionCreateCommand =
        SurveyQuestionCreateCommand(
            name = name,
            description = description,
            questionType = questionType,
            required = required,
            options = options.map { it.toCreateCommand() }
        )
}

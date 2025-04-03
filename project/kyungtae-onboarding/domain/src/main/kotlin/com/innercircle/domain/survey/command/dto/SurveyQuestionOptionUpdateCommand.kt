package com.innercircle.domain.survey.command.dto

data class SurveyQuestionOptionUpdateCommand(
    val id: Long?,
    val content: String,
) {
    fun toCreateCommand(): SurveyQuestionOptionCreateCommand =
        SurveyQuestionOptionCreateCommand(
            content = content,
        )
}

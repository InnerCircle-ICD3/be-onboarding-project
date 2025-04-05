package com.innercircle.domain.survey.command.dto

data class SurveyAnswerOptionCreateCommand(
    val optionId: Long,
    val content: String,
)

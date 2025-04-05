package com.innercircle.domain.survey.command.dto

data class SurveyAnswerCreateCommand(
    val content: String? = null,
    val options: List<SurveyAnswerOptionCreateCommand> = emptyList()
)

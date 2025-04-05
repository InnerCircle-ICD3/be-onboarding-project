package com.innercircle.domain.survey.command.dto

import com.innercircle.survey.entity.QuestionType

data class SurveyQuestionCreateCommand(
    val name: String,
    val description: String,
    val questionType: QuestionType,
    val required: Boolean,
    val options: List<SurveyQuestionOptionCreateCommand>
)

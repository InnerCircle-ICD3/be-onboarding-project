package com.innercircle.presurveyapi.application.survey.command

import com.innercircle.presurveyapi.domain.survey.model.QuestionType

data class CreateSurveyCommand(
    val title: String,
    val description: String,
    val questions: List<CreateQuestionCommand>
)

data class CreateQuestionCommand(
    val title: String,
    val description: String,
    val type: QuestionType,
    val required: Boolean,
    val options: List<String> = emptyList()
)
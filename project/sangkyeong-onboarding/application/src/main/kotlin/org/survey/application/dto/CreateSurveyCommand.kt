package org.survey.application.dto

data class CreateSurveyCommand(
    val title: String,
    val description: String,
    val items: List<CreateSurveyItemCommand>,
)

data class CreateSurveyItemCommand(
    val title: String,
    val description: String,
    val inputType: String,
    val isRequired: Boolean,
    val options: List<CreateItemOptionCommand>? = null,
)

data class CreateItemOptionCommand(
    val value: String,
)

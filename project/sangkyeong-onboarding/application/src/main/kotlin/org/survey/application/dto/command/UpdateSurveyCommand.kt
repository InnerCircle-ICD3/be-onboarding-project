package org.survey.application.dto.command

data class UpdateSurveyCommand(
    val title: String,
    val description: String,
    val items: List<UpdateSurveyItemCommand>,
)

data class UpdateSurveyItemCommand(
    val id: Long? = null,
    val title: String,
    val description: String,
    val inputType: String,
    val isRequired: Boolean,
    val options: List<UpdateItemOptionCommand>? = null,
)

data class UpdateItemOptionCommand(
    val id: Long? = null,
    val value: String,
)

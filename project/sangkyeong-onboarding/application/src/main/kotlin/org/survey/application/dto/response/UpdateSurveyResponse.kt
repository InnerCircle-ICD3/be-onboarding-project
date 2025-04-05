package org.survey.application.dto.response

data class UpdateSurveyResponse(
    val id: Long,
    val title: String,
    val description: String,
    val items: List<UpdateSurveyItemResponse>,
)

data class UpdateSurveyItemResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val inputType: String,
    val isRequired: Boolean,
    val options: List<UpdateItemOptionResponse>,
)

data class UpdateItemOptionResponse(
    val id: Long,
    val surveyItemId: Long,
    val value: String,
)

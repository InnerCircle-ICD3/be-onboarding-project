package org.survey.domain.dto

data class SurveyItemData(
    val title: String,
    val description: String,
    val inputType: String,
    val isRequired: Boolean,
    val options: List<String>? = null,
)

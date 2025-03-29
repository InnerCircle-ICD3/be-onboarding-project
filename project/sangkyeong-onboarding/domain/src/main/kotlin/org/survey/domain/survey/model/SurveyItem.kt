package org.survey.domain.survey.model

class SurveyItem(
    val id: Long = 0,
    val surveyId: Long,
    val title: String,
    val description: String,
    val inputType: String,
    val isRequired: Boolean,
)

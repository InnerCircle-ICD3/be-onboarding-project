package com.onboarding.form.request


data class CreateSurveyDto(
    val title: String,
    val description: String,
    val item: List<CreateQuestionDto>
)

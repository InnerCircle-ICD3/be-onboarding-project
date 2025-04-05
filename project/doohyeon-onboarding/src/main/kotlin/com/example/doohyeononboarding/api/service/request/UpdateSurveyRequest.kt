package com.example.doohyeononboarding.api.service.request

class UpdateSurveyRequest(
    val title: String,
    val description: String? = "",
    val questions: List<QuestionRequest>?
)

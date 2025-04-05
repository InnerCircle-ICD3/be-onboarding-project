package com.example.doohyeononboarding.api.service.request

class CreateSurveyRequest(
    val title: String,
    val description: String? = "",
    val questions: List<QuestionRequest>
)


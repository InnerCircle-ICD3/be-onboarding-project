package com.innercircle.survey.controller.request

import java.time.LocalDateTime

data class SurveyCreateRequest(
    val name: String? = null,
    val description: String? = null,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val questions: List<SurveyQuestionCreateRequest>? = emptyList()
)
package com.innercircle.api.survey.controller.response

import java.time.LocalDateTime

data class SurveyQuestionOptionResponse(
    val id: Long? = null,
    val content: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)

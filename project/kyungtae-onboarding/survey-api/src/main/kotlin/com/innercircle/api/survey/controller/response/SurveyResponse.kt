package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyStatus
import java.time.LocalDateTime

data class SurveyResponse(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val surveyStatus: SurveyStatus? = null,
    val participantCapacity: Int? = null,
    val startedAt: LocalDateTime? = null,
    val endedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val questions: List<SurveyQuestionResponse>? = emptyList(),
    val answers: List<SurveyAnswerResponse>? = emptyList(),
) {
}
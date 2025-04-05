package org.survey.domain.response.model

import java.time.LocalDateTime

data class SurveyResponseDetail(
    val id: Long,
    val surveyId: Long,
    val answers: List<Answer>,
    val createdAt: LocalDateTime,
)

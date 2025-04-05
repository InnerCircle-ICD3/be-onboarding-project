package org.survey.domain.response.model

import java.time.LocalDateTime

class SurveyResponse(
    val id: Long = 0,
    val surveyId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

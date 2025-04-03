package com.innercircle.domain.survey.command.dto

import java.time.LocalDateTime
import java.util.*

data class SurveyUpdateCommand(
    val externalId: UUID,
    val name: String,
    val description: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val participantCapacity: Int,
    val questions: List<SurveyQuestionUpdateCommand>
)

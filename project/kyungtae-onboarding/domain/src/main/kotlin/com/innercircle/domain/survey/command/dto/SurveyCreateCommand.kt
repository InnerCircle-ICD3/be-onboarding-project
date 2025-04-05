package com.innercircle.domain.survey.command.dto

import java.time.LocalDateTime

data class SurveyCreateCommand(
    val name: String,
    val description: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val participantCapacity : Int,
    val questions: List<SurveyQuestionCreateCommand>
)

package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyUpdateCommand
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.*

data class SurveyUpdateRequest(

    @field:NotBlank
    val name: String? = null,

    @field:NotBlank
    val description: String? = null,

    @field:NotNull
    val startAt: LocalDateTime? = null,

    @field:NotNull
    val endAt: LocalDateTime? = null,

    @field:NotNull
    val participantCapacity: Int? = null,

    @field:NotEmpty
    val questions: List<SurveyQuestionUpdateRequest> = emptyList()
) {
    fun toCommand(externalId: UUID): SurveyUpdateCommand = SurveyUpdateCommand(
        externalId = externalId,
        name = name!!,
        description = description!!,
        startAt = startAt!!,
        endAt = endAt!!,
        participantCapacity = participantCapacity!!,
        questions = questions.map { it.toCommand() }
    )
}
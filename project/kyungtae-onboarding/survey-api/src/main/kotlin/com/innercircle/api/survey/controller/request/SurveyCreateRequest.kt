package com.innercircle.api.survey.controller.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.innercircle.domain.survey.command.dto.SurveyCreateCommand
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class SurveyCreateRequest(
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

    val questions: List<SurveyQuestionCreateRequest>? = emptyList()
) {
    fun toCommand() = SurveyCreateCommand(
        name = name!!,
        description = description!!,
        startAt = startAt!!,
        endAt = endAt!!,
        participantCapacity = participantCapacity!!,
        questions = questions!!.map { it.toCommand() }
    )

    @JsonIgnore
    @AssertTrue(message = "startAt은 endAt보다 이전이어야 합니다.")
    fun isStartBeforeEnd(): Boolean {
        return startAt!!.isBefore(endAt)
    }
}
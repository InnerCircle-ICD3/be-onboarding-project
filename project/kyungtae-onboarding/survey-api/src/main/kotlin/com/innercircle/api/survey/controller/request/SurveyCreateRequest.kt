package com.innercircle.api.survey.controller.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.innercircle.domain.survey.command.dto.SurveyCreateCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class SurveyCreateRequest(
    @field:NotBlank
    @Schema(description = "설문 이름", example = "직장인 최애 음식 설문", required = true)
    val name: String? = null,

    @field:NotBlank
    @Schema(description = "설문 설명", example = "식문화에 대한 설문입니다.", required = true)
    val description: String? = null,

    @field:NotNull
    @Schema(description = "설문 시작 예정 시간", example = "2023-10-01T00:00:00", required = true)
    val startAt: LocalDateTime? = null,

    @field:NotNull
    @Schema(description = "설문 종료 예정 시간", example = "2023-10-31T23:59:59", required = true)
    val endAt: LocalDateTime? = null,

    @field:NotNull
    @Schema(description = "설문 참여 인원 수", example = "100", required = true)
    val participantCapacity: Int? = null,

    @field:NotEmpty
    @Schema(description = "설문 질문 목록", required = true)
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
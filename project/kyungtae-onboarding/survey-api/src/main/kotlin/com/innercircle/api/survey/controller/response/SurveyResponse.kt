package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.Survey
import com.innercircle.survey.entity.SurveyStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

data class SurveyResponse(
    @Schema(description = "설문 ID", example = "23df3c4e-2a1b-4d5e-8f3c-4e2b3d5f6a7b", required = true)
    val externalId: UUID? = null,
    @Schema(description = "설문 이름", example = "내가 좋아하는 음식은?", required = true)
    val name: String? = null,
    @Schema(description = "설문 설명", example = "내가 좋아하는 음식은 무엇인가요?", required = true)
    val description: String? = null,
    @Schema(description = "설문 예정 시작일", example = "2023-10-01T12:00:00", required = true)
    val startAt: LocalDateTime? = null,
    @Schema(description = "설문 예정 종료일", example = "2023-10-31T12:00:00", required = true)
    val endAt: LocalDateTime? = null,
    @Schema(description = "설문 상태", example = "READY, IN_PROGRESS, END", required = true)
    val surveyStatus: SurveyStatus? = null,
    @Schema(description = "설문 참여자 수 제한", example = "10", required = true)
    val participantCapacity: Int? = null,
    @Schema(description = "설문 참여자 수", example = "5", required = true)
    val participantCount: Int? = null,
    @Schema(description = "설문 시작일", example = "2023-10-01T12:00:00", required = false)
    val startedAt: LocalDateTime? = null,
    @Schema(description = "설문 종료일", example = "2023-10-31T12:00:00", required = false)
    val endedAt: LocalDateTime? = null,
    @Schema(description = "설문 생성일", example = "2023-10-01T12:00:00", required = true)
    val createdAt: LocalDateTime? = null,
    @Schema(description = "설문 수정일", example = "2023-10-01T12:00:00", required = false)
    val updatedAt: LocalDateTime? = null,
    @Schema(description = "설문 질문", required = true)
    val questions: List<SurveyQuestionResponse>? = emptyList(),
    @Schema(description = "설문 답변", required = false)
    val answers: List<SurveyAnswerResponse>? = emptyList(),
) {
    companion object {
        fun from(survey: Survey): SurveyResponse {
            return SurveyResponse(
                externalId = survey.externalId,
                name = survey.context.name,
                description = survey.context.description,
                startAt = survey.startAt,
                endAt = survey.endAt,
                surveyStatus = survey.status,
                participantCount = survey.participantCount,
                participantCapacity = survey.participantCapacity,
                startedAt = survey.startedAt,
                endedAt = survey.endedAt,
                createdAt = survey.createdAt,
                updatedAt = survey.updatedAt,
                questions = survey.questions.map { SurveyQuestionResponse.from(it) },
                answers = survey.answers.map { SurveyAnswerResponse.from(it) }
            )
        }
    }
}
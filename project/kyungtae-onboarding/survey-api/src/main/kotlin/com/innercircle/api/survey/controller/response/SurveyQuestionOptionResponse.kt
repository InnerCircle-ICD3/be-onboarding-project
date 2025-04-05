package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyQuestionOption
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class SurveyQuestionOptionResponse(
    @Schema(description = "설문 질문 옵션 ID", example = "1", required = true)
    val id: Long? = null,
    @Schema(description = "설문 질문 옵션 내용", example = "순대국", required = true)
    val content: String? = null,
    @Schema(description = "설문 질문 옵션 생성일", example = "2023-10-01T12:00:00", required = true)
    val createdAt: LocalDateTime? = null,
    @Schema(description = "설문 질문 옵션 수정일", example = "2023-10-01T12:00:00", required = false)
    val updatedAt: LocalDateTime? = null,
) {
    companion object {
        fun from(it: SurveyQuestionOption): SurveyQuestionOptionResponse {
            return SurveyQuestionOptionResponse(
                id = it.id,
                content = it.content,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }
    }
}

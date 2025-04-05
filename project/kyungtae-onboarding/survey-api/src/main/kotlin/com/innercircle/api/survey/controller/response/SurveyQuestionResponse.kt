package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyQuestion
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class SurveyQuestionResponse(
    @Schema(description = "설문 질문 ID", example = "1", required = true)
    val id: Long? = null,
    @Schema(description = "설문 질문 이름", example = "어떤 음식을 좋아하시나요?", required = true)
    val name: String? = null,
    @Schema(description = "설문 질문 설명", example = "음식에 대한 선호도를 묻는 질문입니다.", required = false)
    val description: String? = null,
    @Schema(description = "설문 질문 타입", example = "MULTIPLE_CHOICE", required = true)
    val questionType: String? = null,
    @Schema(description = "설문 질문 필수 여부", example = "true", required = true)
    val required: Boolean? = null,
    @Schema(description = "설문 질문 생성일", example = "2023-10-01T12:00:00", required = true)
    val createdAt: LocalDateTime? = null,
    @Schema(description = "설문 질문 수정일", example = "2023-10-01T12:00:00", required = false)
    val updatedAt: LocalDateTime? = null,
    @Schema(description = "설문 질문 옵션", required = false)
    val options: List<SurveyQuestionOptionResponse>? = emptyList()
) {
    companion object {
        fun from(it: SurveyQuestion): SurveyQuestionResponse {
            return SurveyQuestionResponse(
                id = it.id,
                name = it.context.name,
                description = it.context.description,
                questionType = it.questionType.name,
                required = it.required,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                options = it.options.map { SurveyQuestionOptionResponse.from(it) }
            )

        }
    }
}

package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyQuestionUpdateCommand
import com.innercircle.survey.entity.QuestionType
import io.swagger.v3.oas.annotations.media.Schema

data class SurveyQuestionUpdateRequest(
    @Schema(description = "질문 ID (수정일 경우는 필수, null이면 추가)", example = "1", required = false)
    val id: Long? = null,
    @Schema(description = "질문 이름", example = "당신의 최애 음식은?", required = true)
    val name: String? = null,
    @Schema(description = "질문 설명", example = "당신이 가장 좋아하는 음식을 선택해주세요.", required = true)
    val questionType: String? = null,
    @Schema(description = "질문 설명", example = "당신이 가장 좋아하는 음식을 선택해주세요.", required = true)
    val description: String? = null,
    @Schema(description = "질문 옵션 목록", required = false)
    val options: List<SurveyQuestionOptionUpdateRequest>? = emptyList(),
    @Schema(description = "질문 필수 여부", example = "true", required = true)
    val required: Boolean? = false
) {
    fun toCommand(): SurveyQuestionUpdateCommand = SurveyQuestionUpdateCommand(
        id = id!!,
        name = name!!,
        questionType = QuestionType.valueOf(questionType!!),
        description = description!!,
        options = options?.map { it.toCommand() } ?: emptyList(),
        required = required!!
    )
}

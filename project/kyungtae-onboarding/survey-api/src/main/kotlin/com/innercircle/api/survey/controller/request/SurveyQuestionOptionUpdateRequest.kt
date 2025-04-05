package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyQuestionOptionUpdateCommand
import io.swagger.v3.oas.annotations.media.Schema

data class SurveyQuestionOptionUpdateRequest(
    @Schema(description = "답변 옵션 ID (수정일 경우는 필수, null이면 추가)", example = "1", required = false)
    val id: Long? = null,
    @Schema(description = "답변 옵션 내용", example = "순대국", required = true)
    val content: String? = null) {
    fun toCommand() = SurveyQuestionOptionUpdateCommand(
        id = id,
        content = content!!
    )
}

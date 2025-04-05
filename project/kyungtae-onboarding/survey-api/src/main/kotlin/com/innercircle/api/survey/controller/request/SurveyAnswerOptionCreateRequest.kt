package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyAnswerOptionCreateCommand
import io.swagger.v3.oas.annotations.media.Schema

data class SurveyAnswerOptionCreateRequest(
    @Schema(description = "답변 옵션 ID", example = "1", required = true)
    val optionId: Long? = null,
    @Schema(description = "답변 옵션 내용", example = "순대국", required = true)
    val content: String? = null
) {
    fun toCommand(): SurveyAnswerOptionCreateCommand = SurveyAnswerOptionCreateCommand(
        optionId = optionId!!,
        content = content!!
    )
}

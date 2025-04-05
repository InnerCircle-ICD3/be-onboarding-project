package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyQuestionOptionCreateCommand
import io.swagger.v3.oas.annotations.media.Schema

data class SurveyQuestionOptionCreateRequest(
    @Schema(description = "답변 옵션 내용", example = "순대국", required = true)
    val content: String? = null,
) {
    fun toCommand(): SurveyQuestionOptionCreateCommand {
        return SurveyQuestionOptionCreateCommand(
            content = content!!
        )
    }
}

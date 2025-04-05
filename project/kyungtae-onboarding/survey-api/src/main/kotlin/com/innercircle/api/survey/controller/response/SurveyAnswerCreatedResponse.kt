package com.innercircle.api.survey.controller.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class SurveyAnswerCreatedResponse(
    @Schema(description = "생성 된 답변 ID", example = "1", required = true)
    val id: Long? = null
)

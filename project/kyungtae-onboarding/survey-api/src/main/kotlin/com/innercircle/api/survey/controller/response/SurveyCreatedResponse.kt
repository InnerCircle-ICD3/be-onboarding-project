package com.innercircle.api.survey.controller.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class SurveyCreatedResponse(
    @Schema(description = "생성 된 설문 ID", example = "23df3c4e-2a1b-4d5e-8f3c-4e2b3d5f6a7b", required = true)
    val externalId: UUID? = null
)

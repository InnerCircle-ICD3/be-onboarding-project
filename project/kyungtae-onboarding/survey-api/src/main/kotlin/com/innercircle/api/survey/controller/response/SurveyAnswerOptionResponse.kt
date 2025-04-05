package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyAnswerOption
import io.swagger.v3.oas.annotations.media.Schema

data class SurveyAnswerOptionResponse(
    @Schema(description = "답변 ID", example = "1", required = true)
    val surveyAnswerId: Long? = null,
    @Schema(description = "질문 옵션 ID", example = "1", required = true)
    val surveyQuestionOptionId: Long? = null,
    @Schema(description = "답변 옵션 내용", example = "순대국", required = true)
    val content: String? = null
) {
    companion object {
        fun from(surveyAnswerOption: SurveyAnswerOption): SurveyAnswerOptionResponse {
            return SurveyAnswerOptionResponse(
                surveyAnswerId = surveyAnswerOption.id.surveyAnswerId,
                surveyQuestionOptionId = surveyAnswerOption.id.surveyQuestionOptionId,
                content = surveyAnswerOption.content
            )
        }
    }
}

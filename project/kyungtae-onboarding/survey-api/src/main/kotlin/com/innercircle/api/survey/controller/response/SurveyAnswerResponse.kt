package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyAnswer
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class SurveyAnswerResponse(
    @Schema(description = "답변 ID", example = "1", required = true)
    val id: Long? = null,
    @Schema(description = "설문 이름", example = "직장인 최애 음식 설문", required = true)
    val surveyName: String? = null,
    @Schema(description = "설문 설명", example = "식문화에 대한 설문입니다.", required = true)
    val surveyDescription: String? = null,
    @Schema(description = "질문 이름", example = "당신의 최애 음식은?", required = true)
    val surveyQuestionName: String? = null,
    @Schema(description = "질문 설명", example = "당신이 가장 좋아하는 음식을 선택해주세요.", required = true)
    val surveyQuestionDescription: String? = null,
    @Schema(description = "설문 질문 유형", example = "SHORT_ANSWER, LONG_ANSWER, SINGLE_CHOICE, MULTI_CHOICE", required = true)
    val questionType: String? = null,
    @Schema(description = "답변 내용 (선택형일 경우 nul)", example = "순대국", required = false)
    val content: String? = null,
    @Schema(description = "답변 생성일", example = "2023-10-01T12:00:00", required = true)
    val createdAt: LocalDateTime? = null,
    @Schema(description = "답변 수정일", example = "2023-10-01T12:00:00", required = false)
    val updatedAt: LocalDateTime? = null,
    @Schema(description = "답변 옵션", required = false)
    val options: List<SurveyAnswerOptionResponse>? = emptyList()
) {
    companion object {
        fun from(surveyAnswer: SurveyAnswer): SurveyAnswerResponse {
            return SurveyAnswerResponse(
                id = surveyAnswer.id,
                surveyName = surveyAnswer.surveyContext.name,
                surveyDescription = surveyAnswer.surveyContext.description,
                surveyQuestionName = surveyAnswer.surveyQuestionContext.name,
                surveyQuestionDescription = surveyAnswer.surveyQuestionContext.description,
                questionType = surveyAnswer.questionType.name,
                content = surveyAnswer.content,
                createdAt = surveyAnswer.createdAt,
                updatedAt = surveyAnswer.updatedAt,
                options = surveyAnswer.options.map { SurveyAnswerOptionResponse.from(it) }
            )
        }
    }
}

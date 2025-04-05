package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyQuestionCreateCommand
import com.innercircle.survey.entity.QuestionType
import io.swagger.v3.oas.annotations.media.Schema

data class SurveyQuestionCreateRequest(
    @Schema(description = "질문 이름", example = "당신의 최애 음식은?", required = true)
    val name: String? = null,
    @Schema(description = "질문 설명", example = "당신이 가장 좋아하는 음식을 선택해주세요.", required = true)
    val description: String? = null,
    @Schema(description = "설문 질문 유형", example = "SHORT_ANSWER, LONG_ANSWER, SINGLE_CHOICE, MULTI_CHOICE", required = true)
    val questionType: String? = null,
    @Schema(description = "질문 필수 여부", example = "true", required = true)
    val required: Boolean? = null,
    @Schema(description = "질문 옵션 목록", required = false)
    val options: List<SurveyQuestionOptionCreateRequest>? = emptyList()
) {
    fun toCommand(): SurveyQuestionCreateCommand {
        return SurveyQuestionCreateCommand(
            name = name!!,
            description = description!!,
            questionType = QuestionType.valueOf(questionType!!),
            required = required!!,
            options = options?.map { it.toCommand() } ?: emptyList(),
        )
    }
}

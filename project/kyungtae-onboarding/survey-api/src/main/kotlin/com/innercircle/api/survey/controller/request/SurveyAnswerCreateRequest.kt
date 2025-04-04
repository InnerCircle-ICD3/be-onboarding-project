package com.innercircle.api.survey.controller.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.innercircle.domain.survey.command.dto.SurveyAnswerCreateCommand
import com.innercircle.survey.entity.QuestionType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SurveyAnswerCreateRequest(
    @field:NotBlank
    @Schema(description = "설문 이름", example = "직장인 최애 음식 설문", required = true)
    val surveyName: String? = null,

    @field:NotBlank
    @Schema(description = "설문 설명", example = "식문화에 대한 설문입니다.", required = true)
    val surveyDescription: String? = null,

    @field:NotNull
    @Schema(description = "설문 질문 ID", example = "1", required = true)
    val surveyQuestionId: Long? = null,

    @field:NotBlank
    @Schema(description = "설문 질문 이름", example = "가장 좋아하는 음식은?", required = true)
    val surveyQuestionName: String? = null,

    @field:NotBlank
    @Schema(description = "설문 질문 설명", example = "설문 질문 설명", required = true)
    val surveyQuestionDescription: String? = null,

    @field:NotBlank
    @Schema(description = "설문 질문 유형", example = "SHORT_ANSWER, LONG_ANSWER, SINGLE_CHOICE, MULTI_CHOICE", required = true)
    val questionType: String? = null,

    @Schema(description = "답변 내용 (선택형일 경우 비워주세요)", example = "순대국", required = false)
    val content: String? = null,

    @Schema(description = "답변 옵션 (서술형일 경우 비워주세요)", required = false)
    val options: List<SurveyAnswerOptionCreateRequest>? = emptyList()
) {
    @JsonIgnore
    @AssertTrue(message = "선택형 질문에는 옵션이 필요합니다.")
    fun isNotEmptyWhenQuestionIsChoiceType(): Boolean {
        return if (QuestionType.valueOf(questionType!!).isChoiceType()) {
            options?.isNotEmpty() ?: false
        } else {
            true
        }
    }

    @JsonIgnore
    @AssertTrue(message = "서술형 질문에는 옵션이 필요하지 않습니다.")
    fun isEmptyWhenQuestionIsDescriptiveType(): Boolean {
        return if (!QuestionType.valueOf(questionType!!).isChoiceType()) {
            options?.isEmpty() ?: true
        } else {
            true
        }
    }

    fun toCommand(): SurveyAnswerCreateCommand {
        return SurveyAnswerCreateCommand(
            content = content,
            options = options?.map { it.toCommand() } ?: emptyList()
        )
    }
}
package com.innercircle.api.survey.controller.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.innercircle.domain.survey.command.dto.SurveyAnswerCreateCommand
import com.innercircle.survey.entity.QuestionType
import com.innercircle.survey.entity.SurveyQuestion
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class SurveyAnswerCreateRequest(
    @field:NotBlank
    val surveyName: String? = null,

    @field:NotBlank
    val surveyDescription: String? = null,

    @field:NotNull
    val surveyQuestionId: Long? = null,

    @field:NotBlank
    val surveyQuestionName: String? = null,

    @field:NotBlank
    val surveyQuestionDescription: String? = null,

    @field:NotBlank
    val questionType: String? = null,

    @field:NotBlank
    val content: String? = null,

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
            content = content!!,
            options = options?.map { it.toCommand() } ?: emptyList()
        )
    }
}
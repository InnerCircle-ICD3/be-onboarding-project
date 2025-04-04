package com.innercircle.api.survey.controller.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.innercircle.survey.entity.QuestionType
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class SurveyAnswerCreateRequest(
    @field:NotBlank
    val surveyName: String? = null,
    @field:NotBlank
    val surveyDescription: String? = null,
    @field:NotBlank
    val surveyQuestionName: String? = null,
    @field:NotBlank
    val surveyQuestionDescription: String? = null,
    @field:NotBlank
    val questionType: String? = null,
    @field:NotBlank
    val content: String? = null,
    @field:NotNull
    val createdAt: LocalDateTime? = null,
    @field:NotNull
    val updatedAt: LocalDateTime? = null,
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
}
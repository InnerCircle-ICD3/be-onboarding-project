package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyQuestion
import java.time.LocalDateTime

data class SurveyQuestionResponse(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val questionType: String? = null,
    val required: Boolean? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val options: List<SurveyQuestionOptionResponse>? = emptyList()
) {
    companion object {
        fun from(it: SurveyQuestion): SurveyQuestionResponse {
            return SurveyQuestionResponse(
                id = it.id,
                name = it.context.name,
                description = it.context.description,
                questionType = it.questionType.name,
                required = it.required,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
                options = it.options.map { SurveyQuestionOptionResponse.from(it) }
            )

        }
    }
}

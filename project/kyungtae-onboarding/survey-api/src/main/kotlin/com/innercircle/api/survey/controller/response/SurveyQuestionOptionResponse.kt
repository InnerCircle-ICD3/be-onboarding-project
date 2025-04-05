package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyQuestionOption
import java.time.LocalDateTime

data class SurveyQuestionOptionResponse(
    val id: Long? = null,
    val content: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    companion object {
        fun from(it: SurveyQuestionOption): SurveyQuestionOptionResponse {
            return SurveyQuestionOptionResponse(
                id = it.id,
                content = it.content,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }
    }
}

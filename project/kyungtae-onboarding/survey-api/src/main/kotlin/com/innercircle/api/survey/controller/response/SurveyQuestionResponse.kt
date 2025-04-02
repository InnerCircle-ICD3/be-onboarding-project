package com.innercircle.api.survey.controller.response

import com.innercircle.domain.survey.command.dto.SurveyQuestionCreateCommand
import com.innercircle.survey.entity.QuestionType
import java.time.LocalDateTime

data class SurveyQuestionResponse(
    val id: Long? = null,
    val name: String? = null,
    val description: String? = null,
    val inputType: String? = null,
    val required: Boolean? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val options: List<SurveyQuestionOptionResponse>? = emptyList()
) {
}

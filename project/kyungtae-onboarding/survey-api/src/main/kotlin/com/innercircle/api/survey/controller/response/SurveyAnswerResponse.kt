package com.innercircle.api.survey.controller.response

import com.innercircle.domain.survey.command.dto.SurveyQuestionCreateCommand
import com.innercircle.survey.entity.QuestionType
import java.time.LocalDateTime

data class SurveyAnswerResponse(
    val id: Long? = null,
    val surveyName: String? = null,
    val surveyDescription: String? = null,
    val surveyQuestionName: String? = null,
    val surveyQuestionDescription: String? = null,
    val questionType: String? = null,
    val content : String? = null,
    val createdAt : LocalDateTime? = null,
    val updatedAt : LocalDateTime? = null,
    val options: List<SurveyAnswerOptionResponse>? = emptyList()
) {
}

package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyAnswer
import java.time.LocalDateTime

data class SurveyAnswerResponse(
    val id: Long? = null,
    val surveyName: String? = null,
    val surveyDescription: String? = null,
    val surveyQuestionName: String? = null,
    val surveyQuestionDescription: String? = null,
    val questionType: String? = null,
    val content: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
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

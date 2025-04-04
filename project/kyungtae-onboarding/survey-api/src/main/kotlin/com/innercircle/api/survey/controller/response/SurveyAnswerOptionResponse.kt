package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.SurveyAnswerOption

data class SurveyAnswerOptionResponse(
    val surveyAnswerId: Long? = null,
    val surveyQuestionOptionId: Long? = null,
    val content: String? = null
) {
    companion object {
        fun from(surveyAnswerOption: SurveyAnswerOption): SurveyAnswerOptionResponse {
            return SurveyAnswerOptionResponse(
                surveyAnswerId = surveyAnswerOption.id.surveyAnswerId,
                surveyQuestionOptionId = surveyAnswerOption.id.surveyQuestionOptionId,
                content = surveyAnswerOption.content
            )
        }
    }
}

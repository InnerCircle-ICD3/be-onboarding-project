package com.innercircle.api.survey.controller.request

data class SurveyAnswerOptionCreateRequest(
    val optionId: Long? = null,
    val content: String? = null
)

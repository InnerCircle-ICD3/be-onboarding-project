package com.innercircle.survey.controller.request

data class SurveyQuestionCreateRequest(
    val name: String? = null,
    val description: String? = null,
    val inputType: String? = null,
    val required: Boolean? = null,
    val options: List<SurveyQuestionOptionCreateRequest>? = emptyList()
)

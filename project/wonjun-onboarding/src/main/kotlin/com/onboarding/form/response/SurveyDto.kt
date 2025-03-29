package com.onboarding.form.response


data class SurveyDto(
    val id: Long,
    val title: String,
    val description: String,
    val item: List<QuestionDto>
) {

}


package com.onboarding.form.response

import com.onboarding.form.domain.Survey


data class SurveyDto(
    val id: Long,
    val version: Int,
    val title: String,
    val description: String,
    val questions: List<QuestionDto>
) {

    companion object {
        fun of(survey: Survey) =
            SurveyDto(
                survey.id,
                survey.getCurrentVersion().version,
                survey.title,
                survey.description,
                survey.getQuestions().map(QuestionDto::of)
            )
    }

}


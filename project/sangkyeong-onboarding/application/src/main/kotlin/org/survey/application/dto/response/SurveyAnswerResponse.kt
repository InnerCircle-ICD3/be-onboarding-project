package org.survey.application.dto.response

import java.time.LocalDateTime

data class SurveyAnswerResponse(
    val surveyTitle: String,
    val surveyDescription: String,
    val createdAt: LocalDateTime,
    val answers: List<AnswerResponse>,
)

sealed interface AnswerResponse {
    val surveyItemTitle: String
    val surveyItemDescription: String?
}

data class TextAnswerResponse(
    override val surveyItemTitle: String,
    override val surveyItemDescription: String?,
    val value: String,
) : AnswerResponse

data class ChoiceAnswerResponse(
    override val surveyItemTitle: String,
    override val surveyItemDescription: String?,
    val options: List<OptionResponse>,
) : AnswerResponse

data class OptionResponse(
    val value: String,
)

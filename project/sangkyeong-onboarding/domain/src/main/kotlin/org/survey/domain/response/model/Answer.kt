package org.survey.domain.response.model

sealed interface Answer {
    val surveyResponseId: Long
    val surveyItemId: Long
}

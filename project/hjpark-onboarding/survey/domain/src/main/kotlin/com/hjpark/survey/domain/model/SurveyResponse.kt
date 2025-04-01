package com.hjpark.survey.domain.model

data class SurveyResponse(
    val id: Long? = null,
    val surveyId: Long,
    val respondentId: String?,
    val status: ResponseStatus,
    val items: MutableList<ResponseItem> = mutableListOf()
)

enum class ResponseStatus {
    IN_PROGRESS,
    COMPLETED
}
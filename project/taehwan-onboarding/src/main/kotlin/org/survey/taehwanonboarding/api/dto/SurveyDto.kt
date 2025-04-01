package org.survey.taehwanonboarding.api.dto

data class SurveyCreateRequest(
    val title: String,
    val description: String? = null,
    val questions: List<QuestionRequest>
)

data class QuestionRequest(
    val title: String,
    val description: String? = null,
    val required: Boolean = false,
    val orderNumber: Int = 0,
    val type: QuestionType,
    val options: List<String>? = null,
    val maxLength: Int? = null,
    val minSelections: Int? = null,
    val maxSelections: Int? = null
)

enum class QuestionType {
    SHORT_ANSWER,
    LONG_ANSWER,
    SINGLE_SELECTION,
    MULTI_SELECTION
}

data class SurveyCreateResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val status: String,
    val createdAt: String
)
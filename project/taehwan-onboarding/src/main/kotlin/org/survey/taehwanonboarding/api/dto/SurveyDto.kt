package org.survey.taehwanonboarding.api.dto

data class SurveyCreateRequest(
    val title: String,
    val description: String? = null,
    val questions: List<QuestionRequest>,
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
    val maxSelections: Int? = null,
)

enum class QuestionType {
    SHORT_ANSWER,
    LONG_ANSWER,
    SINGLE_SELECTION,
    MULTI_SELECTION,
}

data class SurveyCreateResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val status: String,
    val createdAt: String,
)

// todo: 질문의 답변 중 id가 필요한지에 여부도 고민
data class SurveyDetailResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val items: List<AnswerResponse>,
)

data class AnswerResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val required: Boolean,
    val orderNumber: Int,
    val type: QuestionType,
    val maxLength: Int? = null,
    val options: List<String>? = null,
    val minSelections: Int? = null,
    val maxSelections: Int? = null,
)

data class SurveySummaryResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val status: String,
    val itemCount: Int,
    val createdAt: String,
)

data class ResponseSubmitRequest(
    val respondentId: String? = null,
    val items: List<ResponseItemRequest>,
)

data class ResponseItemRequest(
    val itemId: Long,
    val value: String,
)

data class ResponseSubmitResponse(
    val id: Long,
    val surveyId: Long,
    val status: String,
    val responseAt: String,
)
package com.hjpark.shared.surveyDto

import java.time.LocalDateTime

// 요청 DTO들
data class SubmitResponseRequest(
    val surveyId: Long,
    val respondentId: String? = null,
    val answers: List<AnswerRequest>
)

data class AnswerRequest(
    val questionId: Long,
    val textValue: String? = null,
    val optionIds: List<Long>? = null
)

// 응답 DTO들
data class ResponseSubmissionResponse(
    val responseId: Long,
    val status: String,
    val createTime: LocalDateTime
)

data class ResponseRetrievalResponse(
    val responseId: Long,
    val respondentId: String?,
    val status: String,
    val createTime: LocalDateTime,
    val answers: List<AnswerResponse>
)

data class AnswerResponse(
    val questionId: Long,
    val textValue: String?,
    val selectedOptions: List<OptionResponse>?
)

data class OptionResponse(
    val optionId: Long,
    val text: String,
    val sequence: Int
)

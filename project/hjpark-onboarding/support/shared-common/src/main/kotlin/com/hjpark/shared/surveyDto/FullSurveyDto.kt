package com.hjpark.shared.surveyDto

import java.time.LocalDateTime

data class FullSurveyResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val questions: List<FullQuestionResponse>
)

data class FullQuestionResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val type: String, // "SHORT_ANSWER", "LONG_ANSWER", "SINGLE_CHOICE", "MULTIPLE_CHOICE"
    val required: Boolean,
    val sequence: Short,
    val options: List<FullOptionResponse>
)

data class FullOptionResponse(
    val id: Long,
    val text: String,
    val sequence: Int
)

package com.hjpark.shared.surveyDto

import java.time.LocalDateTime


data class QuestionDto(
    val id: Long,
    val surveyId: Long,
    val name: String,
    val description: String?,
    val type: String,
    val required: Boolean,
    val sequence: Short,
    val createTime: LocalDateTime,
    val options: List<QuestionOptionDto>
)


data class QuestionOptionDto(
    val id: Long,
    val text: String,
    val sequence: Int,
    val createTime: LocalDateTime
)
package com.hjpark.shared.surveyDto

import java.time.LocalDateTime

data class SurveyResponseDto(
    val id: Long,
    val surveyId: Long,
    val respondentId: String?,
    val status: String?,
    val createTime: LocalDateTime,
    val items: List<ResponseItemDto>
)


data class ResponseItemDto(
    val id: Long,
    val surveyResponseId: Long,
    val questionId: Long,
    val textValue: String?,
    val optionId: Long?,
    val createTime: LocalDateTime
)

data class ResponseListDto(
    val responses: List<SurveyResponseDto>
)
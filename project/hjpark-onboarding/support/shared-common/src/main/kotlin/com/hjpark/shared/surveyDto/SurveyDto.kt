package com.hjpark.shared.surveyDto

import java.time.LocalDateTime

data class SurveyDto(
    val id: Long,
    val name: String,
    val description: String?,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime
)

data class SurveyListDto(
    val surveys: List<SurveyDto>
)


// 설문 생성 요청 DTO
data class CreateSurveyRequest(
    val name: String,
    val description: String?,
    val questions: List<CreateQuestionRequest>
)

// 질문 생성 요청 DTO
data class CreateQuestionRequest(
    val name: String,
    val description: String?,
    val type: String, // "SHORT_ANSWER", "LONG_ANSWER", "SINGLE_CHOICE", "MULTIPLE_CHOICE"
    val required: Boolean,
    val sequence: Short,
    val options: List<CreateOptionRequest>? // 선택형 질문에만 필요
)

// 선택지 생성 요청 DTO
data class CreateOptionRequest(
    val text: String,
    val sequence: Int
)

// 설문 수정 요청 DTO (생성과 동일한 구조)
typealias UpdateSurveyRequest = CreateSurveyRequest

// 응답 제출 요청 DTO (새로 추가)
data class SubmitResponseRequest(
    val respondentId: String?,
    val items: List<ResponseItemRequest>
)

// 응답 항목 요청 DTO
data class ResponseItemRequest(
    val questionId: Long,
    val textValue: String?,
    val optionId: Long?
)

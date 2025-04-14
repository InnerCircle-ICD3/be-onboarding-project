package com.hjpark.shared.surveyDto

import java.time.LocalDateTime

// 설문 간단 정보 조회용 Dto
data class SurveyDto(
    val id: Long,
    val name: String,
    val description: String?,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val questionCount: Int
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
data class UpdateSurveyRequest(
    val name: String,
    val description: String?,
    val questions: List<UpdateQuestionRequest>
)

data class UpdateQuestionRequest(
    val id: Long?, // null이면 새 질문으로 간주
    val name: String,
    val description: String?,
    val type: String, // "SHORT_ANSWER", "LONG_ANSWER", "SINGLE_CHOICE", "MULTIPLE_CHOICE"
    val required: Boolean,
    val sequence: Short?,
    val options: List<UpdateOptionRequest>?
)

data class UpdateOptionRequest(
    val id: Long?, // null인 경우 신규 옵션 생성
    val text: String,
    val sequence: Int
)
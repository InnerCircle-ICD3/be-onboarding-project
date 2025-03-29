// src/main/kotlin/com/innercircle/survey/dto/ResponsePayloadDto.kt
package com.innercircle.survey.dto

import com.innercircle.survey.domain.QuestionType
import java.time.LocalDateTime
import jakarta.validation.*

// 설문조사 응답 DTO
data class SurveyResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val questions: List<QuestionResponse>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)

// 질문 항목 응답 DTO
data class QuestionResponse(
    val id: Long,
    val questionName: String,
    val questionDescription: String?,
    val questionType: QuestionType,
    val required: Boolean,
    val options: List<OptionResponse>?,
    val position: Int
)

// 선택지 응답 DTO
data class OptionResponse(
    val id: Long,
    val optionValue: String,
    val position: Int
)

// 설문 응답 정보 DTO
data class ResponseResponse(
    val id: Long,
    val surveyId: Long,
    val items: List<ResponseItemResponse>,
    val createdAt: LocalDateTime
)

// 설문 항목 응답 정보 DTO
data class ResponseItemResponse(
    val id: Long,
    val questionId: Long,
    val questionName: String,
    val questionType: QuestionType,
    val answerValue: String
)
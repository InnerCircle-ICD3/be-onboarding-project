// src/main/kotlin/com/innercircle/survey/dto/ResponseDto.kt
package com.innercircle.survey.dto


import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

// 설문조사 응답 제출 요청 DTO
data class SubmitResponseRequest(
    @field:NotEmpty(message = "응답 항목은 최소 1개 이상이어야 합니다")
    @field:Valid
    val items: List<SubmitResponseItemRequest>
)

// 개별 항목 응답 요청 DTO
data class SubmitResponseItemRequest(
    val questionId: Long,
    val answerValue: String
)

// 설문조사 응답 검색 요청 DTO (고급 기능)
data class SearchResponseRequest(
    val keyword: String? = null // 항목 이름 또는 응답 값을 검색하는 키워드
)
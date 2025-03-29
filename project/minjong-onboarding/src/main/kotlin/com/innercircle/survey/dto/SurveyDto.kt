// src/main/kotlin/com/innercircle/survey/dto/SurveyDto.kt
package com.innercircle.survey.dto

import com.innercircle.survey.domain.QuestionType
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

// 설문조사 생성 요청 DTO
data class CreateSurveyRequest(
    @field:NotBlank(message = "설문조사 이름은 필수입니다")
    val title: String,
    
    val description: String?,
    
    @field:NotEmpty(message = "최소 1개 이상의, 최대 10개까지의 설문 항목이 필요합니다")
    @field:Size(min = 1, max = 10, message = "설문 항목은 1개 이상 10개 이하여야 합니다")
    @field:Valid
    val questions: List<CreateQuestionRequest>
)

// 설문 항목 생성 요청 DTO
data class CreateQuestionRequest(
    @field:NotBlank(message = "항목 이름은 필수입니다")
    val questionName: String,
    
    val questionDescription: String?,
    
    @field:NotBlank(message = "항목 입력 형태는 필수입니다")
    val questionType: String, // "SHORT_ANSWER", "LONG_ANSWER", "SINGLE_SELECT", "MULTI_SELECT"
    
    val required: Boolean = false,
    
    @field:Valid
    val options: List<CreateOptionRequest>? = null,
    
    val position: Int = 0
)

// 선택지 생성 요청 DTO
data class CreateOptionRequest(
    @field:NotBlank(message = "선택지 값은 필수입니다")
    val optionValue: String,
    
    val position: Int = 0
)

// 설문조사 수정 요청 DTO (생성 요청과 구조는 같지만 필드 의미가 다를 수 있어 별도 클래스로 정의)
data class UpdateSurveyRequest(
    @field:NotBlank(message = "설문조사 이름은 필수입니다")
    val title: String,
    
    val description: String?,
    
    @field:NotEmpty(message = "최소 1개 이상의, 최대 10개까지의 설문 항목이 필요합니다")
    @field:Size(min = 1, max = 10, message = "설문 항목은 1개 이상 10개 이하여야 합니다")
    @field:Valid
    val questions: List<UpdateQuestionRequest>
)

// 설문 항목 수정 요청 DTO
data class UpdateQuestionRequest(
    val id: Long?, // 기존 항목 ID (신규 항목은 null)
    
    @field:NotBlank(message = "항목 이름은 필수입니다")
    val questionName: String,
    
    val questionDescription: String?,
    
    @field:NotBlank(message = "항목 입력 형태는 필수입니다")
    val questionType: String,
    
    val required: Boolean = false,
    
    @field:Valid
    val options: List<UpdateOptionRequest>? = null,
    
    val position: Int = 0,
    
    val deleted: Boolean = false // 항목 삭제 여부
)

// 선택지 수정 요청 DTO
data class UpdateOptionRequest(
    val id: Long?, // 기존 선택지 ID (신규 선택지는 null)
    
    @field:NotBlank(message = "선택지 값은 필수입니다")
    val optionValue: String,
    
    val position: Int = 0
)
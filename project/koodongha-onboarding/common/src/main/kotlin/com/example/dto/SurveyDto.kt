package com.example.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

sealed class CreateSurveyItemRequest {
    abstract val name: String
    abstract val description: String?
    abstract val isRequired: Boolean
}

data class TextItemRequest(
    @field:NotBlank
    override val name: String,

    @field:NotBlank
    override val description: String?,

    override val isRequired: Boolean,

    val isLong: Boolean = false
) : CreateSurveyItemRequest()

data class ChoiceItemRequest(
    @field:NotBlank
    override val name: String,

    @field:NotBlank
    override val description: String?,

    override val isRequired: Boolean,

    val isMultiple: Boolean = false,

    @field:Size(min = 1)
    val options: List<String>
) : CreateSurveyItemRequest()

data class CreateSurveyRequest(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val description: String?,

    @field:Size(min = 1, max = 10)
    @field:Valid
    val items: List<CreateSurveyItemRequest>
)

sealed class SurveyItemResponse {
    abstract val id: Long
    abstract val name: String
    abstract val description: String?
    abstract val isRequired: Boolean
    abstract val answers: List<String>?
}

data class TextItemResponse(
    override val id: Long,
    override val name: String,
    override val description: String?,
    override val isRequired: Boolean,
    val isLong: Boolean = false,
    override val answers: List<String>? = null
) : SurveyItemResponse()

data class ChoiceItemResponse(
    override val id: Long,
    override val name: String,
    override val description: String?,
    override val isRequired: Boolean,
    val isMultiple: Boolean = false,
    val options: List<String>,
    override val answers: List<String>? = null
) : SurveyItemResponse()

data class SurveyResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val items: List<SurveyItemResponse>
)

sealed class SurveyItemUpdateRequest {
    abstract val id: Long?
    abstract val name: String
    abstract val description: String?
    abstract val isRequired: Boolean
}

data class TextItemUpdateRequest(
    override val id: Long?,
    override val name: String,
    override val description: String?,
    override val isRequired: Boolean,
    val isLong: Boolean = false
) : SurveyItemUpdateRequest()

data class ChoiceItemUpdateRequest(
    override val id: Long?,
    override val name: String,
    override val description: String?,
    override val isRequired: Boolean,
    val isMultiple: Boolean = false,
    val options: List<String>
) : SurveyItemUpdateRequest()

data class SurveyUpdateRequest(
    val title: String,
    val description: String?,
    val items: List<SurveyItemUpdateRequest>
)

package com.example.dto

sealed class CreateSurveyItemRequest {
    abstract val name: String
    abstract val description: String?
    abstract val isRequired: Boolean
}

data class TextItemRequest(
    override val name: String,
    override val description: String?,
    override val isRequired: Boolean,
    val isLong: Boolean = false
) : CreateSurveyItemRequest()

data class ChoiceItemRequest(
    override val name: String,
    override val description: String?,
    override val isRequired: Boolean,
    val isMultiple: Boolean = false,
    val options: List<String>
) : CreateSurveyItemRequest()

data class CreateSurveyRequest(
    val title: String,
    val description: String?,
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

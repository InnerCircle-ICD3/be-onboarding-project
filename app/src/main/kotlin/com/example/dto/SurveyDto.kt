package com.example.dto
import com.example.entity.InputType

data class CreateSurveyItemRequest(
    val name: String,
    val description: String?,
    val inputType: InputType,
    val isRequired: Boolean,
    val options: List<String>? = null
)

data class CreateSurveyRequest( 
    val title: String,
    val description: String?,
    val items: List<CreateSurveyItemRequest>
)

data class SurveyItemUpdateRequest(
    val id: Long?, 
    val name: String,
    val description: String?,
    val inputType: InputType,
    val isRequired: Boolean,
    val options: List<String>? = null
)

data class SurveyUpdateRequest( 
    val title: String,
    val description: String?,
    val items: List<SurveyItemUpdateRequest>
)

data class SurveyItemResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val inputType: InputType,
    val isRequired: Boolean,
    val options: List<String>?,
    val answers: List<String>?
)

data class SurveyResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val items: List<SurveyItemResponse>
)
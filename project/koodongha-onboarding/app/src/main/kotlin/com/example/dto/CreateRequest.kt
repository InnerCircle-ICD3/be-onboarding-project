package com.example.dto

import com.example.entity.InputType

data class CreateSurveyRequest(
    val title: String,
    val description: String?,
    val items: List<CreateSurveyItemRequest>
)

data class CreateSurveyItemRequest(
    val name: String,
    val description: String?,
    val inputType: InputType,
    val isRequired: Boolean,
    val options: List<String>? = null
)

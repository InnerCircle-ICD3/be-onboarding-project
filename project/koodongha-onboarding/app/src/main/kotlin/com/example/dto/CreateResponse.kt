package com.example.dto

import com.example.entity.InputType

data class SurveyDetailResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val items: List<SurveyItemResponse>
)

data class SurveyItemResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val inputType: InputType,
    val isRequired: Boolean,
    val options: List<String>?
)

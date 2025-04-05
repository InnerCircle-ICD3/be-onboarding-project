package org.survey.application.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class SurveyResponse(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val options: List<SurveyItemResponse>,
)

data class SurveyItemResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val inputType: String,
    @JsonProperty("isRequired")
    val isRequired: Boolean,
    val options: List<ItemOptionResponse>,
)

data class ItemOptionResponse(
    val id: Long,
    val surveyItemId: Long,
    val value: String,
)

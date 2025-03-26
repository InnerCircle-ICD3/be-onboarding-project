package com.onboarding.form.request

import com.onboarding.form.domain.ItemType


data class CreateSurveyDto(
    val title: String,
    val description: String,
    val item: List<CreateItemDto>
)

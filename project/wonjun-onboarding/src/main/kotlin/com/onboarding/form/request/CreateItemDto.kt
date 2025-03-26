package com.onboarding.form.request

import com.onboarding.form.domain.ItemType


data class CreateItemDto(
    val title: String,
    val description: String,
    val itemType: ItemType,
    val isRequired: Boolean,
    val options: List<String>? = null
)
package com.onboarding.form.response

import com.onboarding.form.domain.ItemType

data class ItemDto(
    val title: String,
    val description: String,
    val type: ItemType,
    val isRequired: Boolean,
    val options: List<String>? = null
)
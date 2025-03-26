package com.onboarding.form.response

import com.onboarding.form.domain.ItemType


data class SurveyDto(
    val id:Long,
    val title: String,
    val description: String,
    val item: List<ItemDto>
) {

}


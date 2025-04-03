package com.chosseang.seonghunonboarding.dto

import com.chosseang.seonghunonboarding.entity.Item

data class SurveyCreateRequest(
    val name: String,
    val description: String,
    var items : List<Item>
)

data class ItemCreateRequest(
    val name: String,
    val description: String,
    val type: String,
    val contents: List<String>
)

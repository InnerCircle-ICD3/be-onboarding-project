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

data class SurveyCreateResponse(
    val name: String,
    val description: String,
    val items : List<Item>
)

data class ItemResponse(
    val id: Long?,
    val name: String,
    val description: String,
    val type: String,
    val contents: List<String>
)

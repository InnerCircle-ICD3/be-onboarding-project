package com.example.dto

data class AnswerDto(
    val itemId: Long,
    val values: List<String> 
)

data class AnswerSubmitDto(
    val answers: List<AnswerDto>
)
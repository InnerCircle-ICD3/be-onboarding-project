package com.example.dto

sealed class AnswerDto {
    abstract val itemId: Long
}

data class TextAnswerDto(
    override val itemId: Long,
    val value: String
) : AnswerDto()

data class ChoiceAnswerDto(
    override val itemId: Long,
    val selectedOptionIds: List<Long>
) : AnswerDto()

data class AnswerSubmitDto(
    val answers: List<AnswerDto>
)
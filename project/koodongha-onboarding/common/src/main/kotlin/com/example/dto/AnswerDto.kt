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

data class SurveyAnswerSnapshotDto(
    val questionName: String,
    val questionType: String, // "TEXT" | "CHOICE"
    val values: List<String>
)

data class SurveyAnswerResponse(
    val surveyId: Long,
    val title: String,
    val answers: List<SurveyAnswerSnapshotDto>
)
package com.innercircle.presurveyapi.domain.survey.model

import java.util.*

data class QuestionOption(
    val id: UUID,
    val text: String
)

enum class QuestionType {
    SHORT_TEXT, LONG_TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE;

    fun isChoice(): Boolean = this == SINGLE_CHOICE || this == MULTIPLE_CHOICE
}

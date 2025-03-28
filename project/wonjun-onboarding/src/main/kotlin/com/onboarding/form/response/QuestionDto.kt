package com.onboarding.form.response

import com.onboarding.form.domain.*

sealed class QuestionDto(
    open val title: String,
    open val description: String,
    open val type: QuestionType,
    open val isRequired: Boolean,
) {
    companion object {
        fun of(question: Question): QuestionDto {
            return when (question) {
                is LongQuestion -> StandardQuestionDto(question.title, question.description, question.getType(), question.isRequired)
                is ShortQuestion -> StandardQuestionDto(question.title, question.description, question.getType(), question.isRequired)
                is MultiSelectQuestion -> SelectQuestionDto(question.title, question.description, question.getType(), question.isRequired, question.answerList)
                is SingleSelectQuestion -> SelectQuestionDto(question.title, question.description, question.getType(), question.isRequired, question.answerList)
                else -> throw IllegalArgumentException("Invalid question type")
            }
        }
    }
}

data class StandardQuestionDto(
    override val title: String,
    override val description: String,
    override val type: QuestionType,
    override val isRequired: Boolean
) : QuestionDto(title, description, type, isRequired)

data class SelectQuestionDto(
    override val title: String,
    override val description: String,
    override val type: QuestionType,
    override val isRequired: Boolean,
    val options: List<String>
) : QuestionDto(title, description, type, isRequired)
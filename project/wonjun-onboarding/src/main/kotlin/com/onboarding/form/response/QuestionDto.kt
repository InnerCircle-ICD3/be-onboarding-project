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
                is LongQuestion -> StandardQuestionDto.of(question)
                is ShortQuestion -> StandardQuestionDto.of(question)
                is MultiSelectQuestion -> SelectQuestionDto.of(question)
                is SingleSelectQuestion -> SelectQuestionDto.of(question)
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
) : QuestionDto(title, description, type, isRequired) {
    companion object {
        fun of(question: ShortQuestion) =
            StandardQuestionDto(
                question.title,
                question.description,
                question.type,
                question.isRequired
            )

        fun of(question: LongQuestion) =
            StandardQuestionDto(
                question.title,
                question.description,
                question.type,
                question.isRequired
            )
    }
}

data class SelectQuestionDto(
    override val title: String,
    override val description: String,
    override val type: QuestionType,
    override val isRequired: Boolean,
    val options: List<String>
) : QuestionDto(title, description, type, isRequired) {
    companion object {
        fun of(question: SingleSelectQuestion) =
            SelectQuestionDto(
                question.title,
                question.description,
                question.type,
                question.isRequired,
                question.answerList
            )

        fun of(question: MultiSelectQuestion) =
            SelectQuestionDto(
                question.title,
                question.description,
                question.type,
                question.isRequired,
                question.answerList
            )
    }
}
package com.innercircle.presurveyapi.domain.survey.model

import com.innercircle.presurveyapi.application.survey.command.CreateQuestionCommand
import java.util.*

data class Question(
    val id: UUID?,
    val title: String,
    val description: String,
    val type: QuestionType,
    val required: Boolean,
    val options: List<QuestionOption> = emptyList()
) {
    companion object {
        fun create(command: CreateQuestionCommand): Question {
            if (command.type.isChoice()) {
                require(command.options.isNotEmpty()) {
                    "선택형 질문에는 옵션이 필요합니다."
                }
            }

            return Question(
                id = UUID.randomUUID(),
                title = command.title,
                description = command.description,
                type = command.type,
                required = command.required,
                options = command.options.map { QuestionOption(UUID.randomUUID(), it) }
            )
        }
    }
}

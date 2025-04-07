package com.innercircle.presurveyapi.domain.survey.model

import com.innercircle.presurveyapi.application.survey.command.CreateSurveyCommand
import java.util.*

data class Survey(
    val id: UUID? = null,
    val title: String,
    val description: String,
    val questions: List<Question>
) {
    companion object {
        fun create(command: CreateSurveyCommand): Survey {
            require(command.questions.size in 1..10) {
                "질문은 1개 이상 10개 이하여야 합니다."
            }

            return Survey(
                id = UUID.randomUUID(),
                title = command.title,
                description = command.description,
                questions = command.questions.map { Question.create(it) }
            )
        }
    }
}

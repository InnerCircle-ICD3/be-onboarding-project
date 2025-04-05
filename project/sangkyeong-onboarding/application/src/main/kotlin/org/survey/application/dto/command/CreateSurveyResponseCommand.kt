package org.survey.application.dto.command

import org.survey.domain.response.model.Answer
import org.survey.domain.response.model.ChoiceAnswer
import org.survey.domain.response.model.TextAnswer

data class CreateSurveyResponseCommand(
    val answers: List<AnswerCommand>,
)

sealed interface AnswerCommand {
    val surveyItemId: Long
}

data class TextAnswerCommand(
    override val surveyItemId: Long,
    val value: String,
) : AnswerCommand

data class ChoiceAnswerCommand(
    override val surveyItemId: Long,
    val itemOptionIds: Set<Long>,
) : AnswerCommand

fun CreateSurveyResponseCommand.toDomain(): List<Answer> {
    return answers.map { answer ->
        when (answer) {
            is TextAnswerCommand -> answer.toDomain()
            is ChoiceAnswerCommand -> answer.toDomain()
        }
    }
}

fun TextAnswerCommand.toDomain(): TextAnswer {
    return TextAnswer(
        surveyItemId = this.surveyItemId,
        value = this.value,
    )
}

fun ChoiceAnswerCommand.toDomain(): ChoiceAnswer {
    return ChoiceAnswer(
        surveyItemId = this.surveyItemId,
        itemOptionIds = this.itemOptionIds,
    )
}

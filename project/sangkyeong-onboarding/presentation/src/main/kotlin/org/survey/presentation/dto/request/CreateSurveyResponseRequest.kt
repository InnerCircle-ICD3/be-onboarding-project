package org.survey.presentation.dto.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.survey.application.dto.command.ChoiceAnswerCommand
import org.survey.application.dto.command.CreateSurveyResponseCommand
import org.survey.application.dto.command.TextAnswerCommand

data class CreateSurveyResponseRequest(
    val answers: List<AnswerRequest>,
)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = TextAnswerRequest::class, name = "SHORT_TEXT"),
    JsonSubTypes.Type(value = TextAnswerRequest::class, name = "LONG_TEXT"),
    JsonSubTypes.Type(value = ChoiceAnswerRequest::class, name = "SINGLE_CHOICE"),
    JsonSubTypes.Type(value = ChoiceAnswerRequest::class, name = "MULTIPLE_CHOICE"),
)
sealed interface AnswerRequest {
    val surveyItemId: Long
}

data class TextAnswerRequest(
    override val surveyItemId: Long,
    val value: String,
) : AnswerRequest

data class ChoiceAnswerRequest(
    override val surveyItemId: Long,
    val itemOptionIds: Set<Long>,
) : AnswerRequest

fun CreateSurveyResponseRequest.toCommand(): CreateSurveyResponseCommand {
    return CreateSurveyResponseCommand(
        answers =
            answers.map { answer ->
                when (answer) {
                    is TextAnswerRequest ->
                        TextAnswerCommand(
                            surveyItemId = answer.surveyItemId,
                            value = answer.value,
                        )
                    is ChoiceAnswerRequest ->
                        ChoiceAnswerCommand(
                            surveyItemId = answer.surveyItemId,
                            itemOptionIds = answer.itemOptionIds,
                        )
                }
            },
    )
}

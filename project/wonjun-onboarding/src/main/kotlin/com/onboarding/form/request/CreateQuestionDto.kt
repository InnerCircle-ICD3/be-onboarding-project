package com.onboarding.form.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.onboarding.form.domain.QuestionType


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(name = QuestionType.SHORT_VALUE, value = CreateStandardQuestionDto::class),
    JsonSubTypes.Type(name = QuestionType.LONG_VALUE, value = CreateStandardQuestionDto::class),
    JsonSubTypes.Type(name = QuestionType.SINGLE_SELECT_VALUE, value = CreateSelectQuestionDto::class),
    JsonSubTypes.Type(name = QuestionType.MULTI_SELECT_VALUE, value = CreateSelectQuestionDto::class),
)
sealed class CreateQuestionDto(
    open val title: String,
    open val description: String,
    open val type: QuestionType,
    open val isRequired: Boolean,
)

data class CreateStandardQuestionDto(
    override val title: String,
    override val description: String,
    override val type: QuestionType,
    override val isRequired: Boolean,
) : CreateQuestionDto(title, description, type, isRequired)

data class CreateSelectQuestionDto(
    override val title: String,
    override val description: String,
    override val type: QuestionType,
    override val isRequired: Boolean,
    val answerList: List<String>
) : CreateQuestionDto(title, description, type, isRequired)
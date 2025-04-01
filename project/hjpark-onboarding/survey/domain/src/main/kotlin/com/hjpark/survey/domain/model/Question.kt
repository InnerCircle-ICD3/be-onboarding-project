package com.hjpark.survey.domain.model

import java.time.LocalDateTime

data class Question(
    val id: Long? = null,
    val name: String,
    val description: String?,
    val type: QuestionType,
    val required: Boolean,
    val order: Int,
    val options: MutableList<QuestionOption> = mutableListOf()
) {
    val createTime: LocalDateTime = LocalDateTime.now()

    fun addOption(option: QuestionOption) {
        if (requiresOptions()) {
            options.add(option)
        }
    }

    fun removeOption(option: QuestionOption) {
        if (requiresOptions()) {
            options.remove(option)
        }
    }

    fun validateResponse(textValue: String?, optionId: Long?): Boolean {
        if (!required && textValue == null && optionId == null) {
            return true
        }

        return when (type) {
            QuestionType.SHORT_ANSWER, QuestionType.LONG_ANSWER -> !textValue.isNullOrBlank()
            QuestionType.SINGLE_CHOICE -> optionId != null && options.any { it.id == optionId }
            QuestionType.MULTIPLE_CHOICE -> textValue?.split(",")?.all { optionIdStr ->
                optionIdStr.toLongOrNull()?.let { id -> options.any { it.id == id } } ?: false
            } ?: false
        }
    }

    fun requiresOptions(): Boolean {
        return type == QuestionType.SINGLE_CHOICE || type == QuestionType.MULTIPLE_CHOICE
    }
}
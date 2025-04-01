package com.hjpark.survey.domain.model

import java.time.LocalDateTime

class ResponseItem(
    val id: Long = 0L,
    val questionId: Long,
    var textValue: String? = null,
    var optionId: Long? = null,
    val createTime: LocalDateTime = LocalDateTime.now()
) {
    fun validate(question: Question): Boolean {
        return question.validateResponse(textValue, optionId)
    }
} 
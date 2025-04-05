package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyAnswerOptionCreateCommand

data class SurveyAnswerOptionCreateRequest(
    val optionId: Long? = null,
    val content: String? = null
) {
    fun toCommand(): SurveyAnswerOptionCreateCommand = SurveyAnswerOptionCreateCommand(
        optionId = optionId!!,
        content = content!!
    )
}

package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyQuestionOptionCreateCommand

data class SurveyQuestionOptionCreateRequest(
    val content: String? = null,
) {
    fun toCommand(): SurveyQuestionOptionCreateCommand {
        return SurveyQuestionOptionCreateCommand(
            content = content!!
        )
    }
}

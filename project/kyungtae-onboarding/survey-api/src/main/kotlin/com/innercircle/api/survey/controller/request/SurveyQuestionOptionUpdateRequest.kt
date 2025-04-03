package com.innercircle.api.survey.controller.request

import com.innercircle.domain.survey.command.dto.SurveyQuestionOptionUpdateCommand

data class SurveyQuestionOptionUpdateRequest(val id: Long? = null, val content: String? = null) {
    fun toCommand() = SurveyQuestionOptionUpdateCommand(
        id = id,
        content = content!!
    )
}

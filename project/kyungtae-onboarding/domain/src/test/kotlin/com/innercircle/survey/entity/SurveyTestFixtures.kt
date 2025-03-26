package com.innercircle.survey.entity

import com.innercircle.domain.survey.command.dto.SurveyCreateCommand
import java.time.LocalDateTime

object SurveyTestFixtures {
    private val now = LocalDateTime.now()
    private val tomorrow = now.plusDays(1)

    fun survey(
        surveyName: String = "설문 이름",
        surveyDescription: String = "설문 설명"
    ): Survey {
        return surveyCreateCommand(surveyName, surveyDescription).let { Survey.from(it) }
    }

    fun surveyCreateCommand(
        surveyName: String,
        surveyDescription: String,
        startAt: LocalDateTime = now,
        endAt: LocalDateTime = tomorrow
    ) = SurveyCreateCommand(
        surveyName,
        surveyDescription,
        startAt,
        endAt,
        10,
        emptyList()
    )
}
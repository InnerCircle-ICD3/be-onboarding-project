package com.innercircle.api.survey.controller.response

import com.innercircle.survey.entity.QuestionType
import com.innercircle.survey.entity.Survey
import com.innercircle.survey.entity.SurveyStatus
import java.time.LocalDateTime
import java.util.*

data class SurveyResponse(
    val externalId: UUID? = null,
    val name: String? = null,
    val description: String? = null,
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime? = null,
    val surveyStatus: SurveyStatus? = null,
    val participantCapacity: Int? = null,
    val participantCount: Int? = null,
    val startedAt: LocalDateTime? = null,
    val endedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val questions: List<SurveyQuestionResponse>? = emptyList(),
    val answers: List<SurveyAnswerResponse>? = emptyList(),
) {
    companion object {
        fun from(survey: Survey): SurveyResponse {
            return SurveyResponse(
                externalId = survey.externalId,
                name = survey.context.name,
                description = survey.context.description,
                startAt = survey.startAt,
                endAt = survey.endAt,
                surveyStatus = survey.status,
                participantCount = survey.participantCount,
                participantCapacity = survey.participantCapacity,
                startedAt = survey.startedAt,
                endedAt = survey.endedAt,
                createdAt = survey.createdAt,
                updatedAt = survey.updatedAt,
                questions = survey.questions.map { SurveyQuestionResponse.from(it) },
                answers = survey.answers.map { SurveyAnswerResponse.from(it) }
            )
        }
    }
}
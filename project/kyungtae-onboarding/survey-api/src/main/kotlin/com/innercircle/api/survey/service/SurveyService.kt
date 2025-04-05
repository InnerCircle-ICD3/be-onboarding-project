package com.innercircle.api.survey.service

import com.innercircle.api.survey.controller.request.SurveyAnswerCreateRequest
import com.innercircle.api.survey.controller.request.SurveyCreateRequest
import com.innercircle.api.survey.controller.request.SurveyUpdateRequest
import com.innercircle.api.survey.controller.response.SurveyAnswerCreatedResponse
import com.innercircle.api.survey.controller.response.SurveyCreatedResponse
import com.innercircle.api.survey.controller.response.SurveyResponse
import com.innercircle.domain.survey.command.service.SurveyAnswerCommandService
import com.innercircle.domain.survey.command.service.SurveyCommandService
import com.innercircle.domain.survey.query.service.SurveyQueryService
import org.springframework.stereotype.Service
import java.util.*

@Service
class SurveyService(
    private val surveyCommandService: SurveyCommandService,
    private val surveyQueryService: SurveyQueryService,

    private val surveyAnswerCommandService: SurveyAnswerCommandService
) {
    fun createSurvey(request: SurveyCreateRequest): SurveyCreatedResponse =
        SurveyCreatedResponse(surveyCommandService.create(request.toCommand()).externalId)

    fun getSurvey(id: UUID): SurveyResponse =
        surveyQueryService.fetchSurveyAggregateOrThrow(id)
            .let { SurveyResponse.from(it) }

    fun updateSurvey(id: UUID, request: SurveyUpdateRequest) {
        surveyCommandService.update(id, request.toCommand(id))
    }

    fun answerSurvey(surveyId: UUID, request: SurveyAnswerCreateRequest): SurveyAnswerCreatedResponse =
        SurveyAnswerCreatedResponse(
            surveyQueryService.fetchSurveyAggregateOrThrow(surveyId)
                .questions.first { it.id == request.surveyQuestionId }
                .also {
                    check(surveyCommandService.increaseParticipantCount(surveyId)) { "설문 참여 인원이 초과되었습니다." }
                }.let {
                    surveyAnswerCommandService.create(
                        surveyQuestionId = it.id!!,
                        command = request.toCommand()
                    ).id
                }
        )
}
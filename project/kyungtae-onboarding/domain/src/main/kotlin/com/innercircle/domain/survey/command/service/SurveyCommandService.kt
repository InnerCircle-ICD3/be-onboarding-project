package com.innercircle.domain.survey.command.service

import com.innercircle.common.CommandService
import com.innercircle.domain.survey.command.dto.SurveyCreateCommand
import com.innercircle.domain.survey.command.dto.SurveyUpdateCommand
import com.innercircle.domain.survey.repository.SurveyRepository
import com.innercircle.survey.entity.Survey
import java.util.*

@CommandService
class SurveyCommandService(
    private val surveyRepository: SurveyRepository
) {
    fun create(command: SurveyCreateCommand): Survey =
        surveyRepository.save(Survey.from(command))

    fun update(id: UUID, command: SurveyUpdateCommand) {
        val survey = surveyRepository.fetchSurveyQuestions(id).orElseThrow()
        survey.update(command)
    }

    fun increaseParticipantCount(surveyId: UUID): Boolean = surveyRepository.increaseParticipantCount(surveyId)
}
package com.innercircle.domain.survey.command.service

import com.innercircle.common.CommandService
import com.innercircle.domain.survey.command.dto.SurveyCreateCommand
import com.innercircle.domain.survey.repository.SurveyRepository
import com.innercircle.survey.entity.Survey

@CommandService
class SurveyCommandService {

    lateinit var surveyRepository: SurveyRepository

    fun create(command: SurveyCreateCommand): Survey {
        val survey = Survey.from(command)

        return surveyRepository.save(survey)
    }

}
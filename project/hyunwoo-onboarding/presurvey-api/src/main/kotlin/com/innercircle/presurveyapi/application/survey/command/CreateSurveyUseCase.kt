package com.innercircle.presurveyapi.application.survey.command

import com.innercircle.presurveyapi.domain.survey.model.Survey
import com.innercircle.presurveyapi.domain.survey.repository.SurveyRepository

class CreateSurveyUseCase(
    private val surveyRepository: SurveyRepository
) {
    fun invoke(command: CreateSurveyCommand): Survey {
        val survey = Survey.create(command)
        return surveyRepository.save(survey)
    }
}
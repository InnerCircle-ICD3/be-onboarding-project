package com.innercircle.presurveyapi.domain.survey.repository

import com.innercircle.presurveyapi.domain.survey.model.Survey

interface SurveyRepository {
    fun save(survey: Survey): Survey
}
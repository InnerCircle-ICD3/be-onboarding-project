package org.survey.domain.survey.repository

import org.survey.domain.survey.model.Survey

interface SurveyRepository {
    fun save(survey: Survey): Long
}

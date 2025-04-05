package org.survey.domain.response.repository

import org.survey.domain.response.model.SurveyResponse

interface SurveyResponseRepository {
    fun save(surveyResponse: SurveyResponse): Long

    fun findById(id: Long): SurveyResponse?

    fun findAllBySurveyId(surveyId: Long): List<SurveyResponse>
}

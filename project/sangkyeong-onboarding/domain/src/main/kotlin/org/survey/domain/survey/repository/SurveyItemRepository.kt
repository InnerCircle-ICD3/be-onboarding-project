package org.survey.domain.survey.repository

import org.survey.domain.survey.model.SurveyItem

interface SurveyItemRepository {
    fun save(surveyItem: SurveyItem): Long

    fun saveAll(surveyItems: List<SurveyItem>): List<SurveyItem>
}

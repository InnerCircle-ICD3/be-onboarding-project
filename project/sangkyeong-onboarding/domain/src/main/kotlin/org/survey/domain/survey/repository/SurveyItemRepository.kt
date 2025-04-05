package org.survey.domain.survey.repository

import org.survey.domain.survey.model.SurveyItem

interface SurveyItemRepository {
    fun save(surveyItem: SurveyItem): Long

    fun saveAll(surveyItems: List<SurveyItem>): List<SurveyItem>

    fun findBySurveyId(surveyId: Long): List<SurveyItem>

    fun findAllById(surveyItemIds: List<Long>): List<SurveyItem>

    fun deleteItems(surveyItems: List<SurveyItem>)

    fun updateItems(surveyItems: List<SurveyItem>)
}

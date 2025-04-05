package org.survey.domain.survey.service

import org.springframework.stereotype.Service
import org.survey.domain.survey.model.ItemOption
import org.survey.domain.survey.model.Survey
import org.survey.domain.survey.model.SurveyItem
import org.survey.domain.survey.repository.ItemOptionRepository
import org.survey.domain.survey.repository.SurveyItemRepository
import org.survey.domain.survey.repository.SurveyRepository

@Service
class SurveyQueryService(
    private val surveyRepository: SurveyRepository,
    private val surveyItemRepository: SurveyItemRepository,
    private val itemOptionRepository: ItemOptionRepository,
) {
    fun getSurvey(surveyId: Long): Survey {
        return surveyRepository.findById(surveyId)
            ?: throw IllegalArgumentException("설문을 찾을 수 없습니다.")
    }

    fun getSurveyItems(surveyId: Long): List<SurveyItem> {
        return surveyItemRepository.findBySurveyId(surveyId)
    }

    fun getItemOptions(surveyItemIds: List<Long>): List<ItemOption> {
        return itemOptionRepository.findBySurveyItemsIdIn(surveyItemIds)
    }

    fun getItemOptionsGroupBySurveyItem(itemIds: List<Long>): Map<Long, List<ItemOption>> {
        val options = itemOptionRepository.findBySurveyItemsIdIn(itemIds)
        return options.groupBy { it.surveyItemId }
    }
}

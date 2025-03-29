package org.survey.domain.survey.service

import org.springframework.stereotype.Service
import org.survey.domain.dto.SurveyItemData
import org.survey.domain.survey.model.ItemOption
import org.survey.domain.survey.model.Survey
import org.survey.domain.survey.model.SurveyItem
import org.survey.domain.survey.repository.ItemOptionRepository
import org.survey.domain.survey.repository.SurveyItemRepository
import org.survey.domain.survey.repository.SurveyRepository

@Service
class SurveyDomainService(
    private val surveyRepository: SurveyRepository,
    private val surveyItemRepository: SurveyItemRepository,
    private val itemOptionRepository: ItemOptionRepository,
) {
    fun createSurvey(
        title: String,
        description: String,
        items: List<SurveyItemData>,
    ) {
        validateSurveyItems(items)

        val surveyId =
            surveyRepository.save(
                Survey(
                    title = title,
                    description = description,
                ),
            )

        val surveyItems =
            items.map { itemData ->
                SurveyItem(
                    surveyId = surveyId,
                    title = itemData.title,
                    description = itemData.description,
                    inputType = itemData.inputType,
                    isRequired = itemData.isRequired,
                )
            }
        val savedItems = surveyItemRepository.saveAll(surveyItems)

        val itemOptionsMap = mutableMapOf<SurveyItem, List<String>>()

        items.forEachIndexed { index, itemData ->
            itemData.options?.let {
                itemOptionsMap[savedItems[index]] = it
            }
        }

        val allOptions =
            itemOptionsMap.flatMap { (item, options) ->
                options.map { optionValue ->
                    ItemOption(
                        surveyItemId = item.id,
                        value = optionValue,
                    )
                }
            }
        itemOptionRepository.saveAll(allOptions)
    }

    private fun validateSurveyItems(items: List<SurveyItemData>) {
        if (items.isEmpty()) {
            throw IllegalArgumentException("설문 항목이 최소 1개 이상 필요하다.")
        }

        if (items.size > 10) {
            throw IllegalArgumentException("설문 항목은 최대 10개까지 가능하다.")
        }

        items.forEach { item ->
            if (item.inputType !in listOf("SINGLE_CHOICE", "MULTIPLE_CHOICE") && !item.options.isNullOrEmpty()) {
                throw IllegalArgumentException("선택형 질문이 아니면 선택지가 없어야 한다.")
            }

            if (item.inputType in listOf("SINGLE_CHOICE", "MULTIPLE_CHOICE") && item.options.isNullOrEmpty()) {
                throw IllegalArgumentException("선택형 질문에는 최소 1개 이상의 선택지가 필요하다.")
            }
        }
    }
}

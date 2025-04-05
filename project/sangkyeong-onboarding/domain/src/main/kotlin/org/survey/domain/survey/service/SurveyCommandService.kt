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
class SurveyCommandService(
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
            throw IllegalArgumentException("설문 항목이 최소 1개 이상 필요합니다.")
        }

        if (items.size > 10) {
            throw IllegalArgumentException("설문 항목은 최대 10개까지 가능합니다.")
        }

        items.forEach { item ->
            if (item.inputType !in listOf("SINGLE_CHOICE", "MULTIPLE_CHOICE") && !item.options.isNullOrEmpty()) {
                throw IllegalArgumentException("선택형 질문이 아니면 선택지가 없어야 합니다.")
            }

            if (item.inputType in listOf("SINGLE_CHOICE", "MULTIPLE_CHOICE") && item.options.isNullOrEmpty()) {
                throw IllegalArgumentException("선택형 질문에는 최소 1개 이상의 선택지가 필요합니다.")
            }
        }
    }

    fun updateSurvey(
        surveyId: Long,
        title: String,
        description: String,
    ) {
        val survey =
            surveyRepository.findById(surveyId)
                ?: throw IllegalArgumentException("설문조사를 찾을 수 없습니다.")

        survey.update(title, description)

        surveyRepository.save(survey)
    }

    fun deleteNotUsingSurveyItems(
        existingItems: List<SurveyItem>,
        requestItemIds: Set<Long>,
    ) {
        val itemsToDelete =
            existingItems
                .filter { it.id !in requestItemIds }
                .map { it.id }

        val surveyItems = surveyItemRepository.findAllById(itemsToDelete)

        surveyItems.forEach {
            it.markAsDeleted()
        }

        surveyItemRepository.deleteItems(surveyItems)
    }

    fun addSurveyItems(
        surveyId: Long,
        newItems: List<SurveyItem>,
        options: List<String>? = null,
    ) {
        val savedItems = surveyItemRepository.saveAll(newItems)

        val itemOptionsMap = mutableMapOf<SurveyItem, List<String>>()

        newItems.forEachIndexed { index, item ->
            item.options?.let {
                if (it.isNotEmpty()) {
                    itemOptionsMap[savedItems[index]] = it
                }
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

        if (allOptions.isNotEmpty()) {
            itemOptionRepository.saveAll(allOptions)
        }
    }

    fun updateSurveyItems(surveyItems: List<SurveyItem>) {
        val itemDataMap = surveyItems.associateBy { it.id }

        val itemIds = surveyItems.map { it.id }

        val existingItems = surveyItemRepository.findAllById(itemIds)

        existingItems.forEach { item ->
            val itemData = itemDataMap[item.id] ?: return@forEach

            item.update(
                title = itemData.title,
                description = itemData.description,
                inputType = itemData.inputType,
                isRequired = itemData.isRequired,
            )
        }

        surveyItemRepository.updateItems(existingItems)
    }
}

package org.survey.domain.survey.repository

import org.survey.domain.survey.model.ItemOption

interface ItemOptionRepository {
    fun save(itemOption: ItemOption): Long

    fun saveAll(itemOptions: List<ItemOption>)

    fun findBySurveyItemsIdIn(itemIds: List<Long>): List<ItemOption>

    fun saveOptions(
        itemId: Long,
        options: List<String>,
    )

    fun findAllById(optionIds: List<Long>): List<ItemOption>

    fun deleteOptions(itemOptions: List<ItemOption>)
}

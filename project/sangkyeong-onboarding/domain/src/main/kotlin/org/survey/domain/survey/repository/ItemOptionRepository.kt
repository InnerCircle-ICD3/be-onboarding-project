package org.survey.domain.survey.repository

import org.survey.domain.survey.model.ItemOption

interface ItemOptionRepository {
    fun save(itemOption: ItemOption): Long

    fun saveAll(itemOptions: List<ItemOption>)

    fun findBySurveyItemIdIn(itemIds: List<Long>): List<ItemOption>
}

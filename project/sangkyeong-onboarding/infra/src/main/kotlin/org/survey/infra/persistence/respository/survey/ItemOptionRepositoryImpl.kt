package org.survey.infra.persistence.respository.survey

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.survey.domain.survey.model.ItemOption
import org.survey.domain.survey.repository.ItemOptionRepository
import org.survey.infra.persistence.entity.survey.ItemOptionEntity
import org.survey.infra.persistence.toDomain
import org.survey.infra.persistence.toEntity

interface ItemOptionJpaRepository : JpaRepository<ItemOptionEntity, Long> {
    fun findBySurveyItemIdInAndIsDeletedIsFalse(itemIds: List<Long>): List<ItemOptionEntity>

    @Modifying
    @Query("UPDATE ItemOptionEntity s SET s.isDeleted = true WHERE s.id IN :ids")
    fun markItemsOptionsAsDeletedById(ids: List<Long>)
}

@Repository
class ItemOptionRepositoryImpl(
    private val itemOptionJpaRepository: ItemOptionJpaRepository,
) : ItemOptionRepository {
    override fun save(itemOption: ItemOption): Long {
        return itemOptionJpaRepository.save(itemOption.toEntity()).id
    }

    override fun saveAll(itemOptions: List<ItemOption>) {
        itemOptionJpaRepository.saveAll(itemOptions.map { it.toEntity() })
    }

    override fun findBySurveyItemsIdIn(itemIds: List<Long>): List<ItemOption> {
        return itemOptionJpaRepository.findBySurveyItemIdInAndIsDeletedIsFalse(itemIds).map { it.toDomain() }
    }

    override fun saveOptions(
        itemId: Long,
        options: List<String>,
    ) {
        val entities =
            options.map { value ->
                ItemOptionEntity(
                    surveyItemId = itemId,
                    value = value,
                )
            }
        itemOptionJpaRepository.saveAll(entities)
    }

    override fun deleteOptions(itemOptions: List<ItemOption>) {
        itemOptionJpaRepository.markItemsOptionsAsDeletedById(itemOptions.map { it.id })
    }

    override fun findAllById(optionIds: List<Long>): List<ItemOption> {
        return itemOptionJpaRepository.findAllById(optionIds).map { it.toDomain() }
    }
}

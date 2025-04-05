package org.survey.infra.persistence.respository.survey

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.survey.domain.survey.model.SurveyItem
import org.survey.domain.survey.repository.SurveyItemRepository
import org.survey.infra.persistence.entity.survey.SurveyItemEntity
import org.survey.infra.persistence.toDomain
import org.survey.infra.persistence.toEntity

interface SurveyItemJpaRepository : JpaRepository<SurveyItemEntity, Long> {
    fun findBySurveyIdAndIsDeletedIsFalse(surveyId: Long): List<SurveyItemEntity>

    @Modifying
    @Query("UPDATE SurveyItemEntity s SET s.isDeleted = true WHERE s.id IN :ids")
    fun markItemsAsDeletedById(ids: List<Long>): Int
}

@Repository
class SurveyItemRepositoryImpl(
    private val surveyItemJpaRepository: SurveyItemJpaRepository,
) : SurveyItemRepository {
    override fun save(surveyItem: SurveyItem): Long {
        return surveyItemJpaRepository.save(surveyItem.toEntity()).id
    }

    override fun saveAll(surveyItems: List<SurveyItem>): List<SurveyItem> {
        return surveyItemJpaRepository.saveAll(surveyItems.map { it.toEntity() }).map { it.toDomain() }
    }

    override fun findBySurveyId(surveyId: Long): List<SurveyItem> {
        return surveyItemJpaRepository.findBySurveyIdAndIsDeletedIsFalse(surveyId).map { it.toDomain() }
    }

    override fun findAllById(surveyItemIds: List<Long>): List<SurveyItem> {
        return surveyItemJpaRepository.findAllById(surveyItemIds).map { it.toDomain() }
    }

    override fun deleteItems(surveyItems: List<SurveyItem>) {
        surveyItemJpaRepository.markItemsAsDeletedById(surveyItems.map { it.id })
    }

    override fun updateItems(surveyItems: List<SurveyItem>) {
        val map = surveyItems.associateBy { it.id }
        val surveyItemEntities = surveyItemJpaRepository.findAllById(map.keys).associateBy { it.id }
        surveyItemEntities.forEach { (id, entity) ->
            val surveyItem = map[id] ?: return@forEach
            entity.update(
                title = surveyItem.title,
                description = surveyItem.description,
                inputType = surveyItem.inputType,
                isRequired = surveyItem.isRequired,
            )
        }
        surveyItemJpaRepository.saveAll(surveyItemEntities.values)
    }
}

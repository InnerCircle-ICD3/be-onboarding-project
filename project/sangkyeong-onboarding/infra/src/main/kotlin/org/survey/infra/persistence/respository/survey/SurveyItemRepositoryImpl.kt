package org.survey.infra.persistence.respository.survey

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.survey.domain.survey.model.SurveyItem
import org.survey.domain.survey.repository.SurveyItemRepository
import org.survey.infra.persistence.entity.survey.SurveyItemEntity
import org.survey.infra.persistence.toDomain
import org.survey.infra.persistence.toEntity

interface SurveyItemJpaRepository : JpaRepository<SurveyItemEntity, Long> {
    fun findBySurveyIdAndIsDeletedIsFalse(surveyId: Long): List<SurveyItemEntity>
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
}

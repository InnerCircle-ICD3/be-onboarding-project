package org.survey.infra.persistence.respository.survey

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.survey.domain.survey.model.ItemOption
import org.survey.domain.survey.repository.ItemOptionRepository
import org.survey.infra.persistence.entity.survey.ItemOptionEntity
import org.survey.infra.persistence.toEntity

interface ItemOptionJpaRepository : JpaRepository<ItemOptionEntity, Long>

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
}

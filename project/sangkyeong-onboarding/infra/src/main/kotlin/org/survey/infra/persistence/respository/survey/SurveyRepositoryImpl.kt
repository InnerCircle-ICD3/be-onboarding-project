package org.survey.infra.persistence.respository.survey

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.survey.domain.survey.model.Survey
import org.survey.domain.survey.repository.SurveyRepository
import org.survey.infra.persistence.entity.survey.SurveyEntity
import org.survey.infra.persistence.toDomain
import org.survey.infra.persistence.toEntity

interface SurveyJpaRepository : JpaRepository<SurveyEntity, Long>

@Repository
class SurveyRepositoryImpl(
    private val surveyJpaRepository: SurveyJpaRepository,
) : SurveyRepository {
    override fun save(survey: Survey): Long {
        val entityToSave =
            if (survey.id > 0) {
                val existingEntity =
                    surveyJpaRepository.findByIdOrNull(survey.id)
                        ?: throw IllegalArgumentException("업데이트할 설문조사가 없습니다")

                existingEntity.updateFromDomain(survey.title, survey.description)
                existingEntity
            } else {
                survey.toEntity()
            }
        return surveyJpaRepository.save(entityToSave).id
    }

    override fun findById(id: Long): Survey? {
        return surveyJpaRepository.findByIdOrNull(id)?.toDomain()
    }
}

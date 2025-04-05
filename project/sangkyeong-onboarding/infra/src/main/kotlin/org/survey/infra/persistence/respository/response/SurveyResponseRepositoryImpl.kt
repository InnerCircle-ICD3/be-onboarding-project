package org.survey.infra.persistence.respository.response

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.survey.domain.response.model.SurveyResponse
import org.survey.domain.response.repository.SurveyResponseRepository
import org.survey.infra.persistence.entity.response.SurveyResponseEntity

interface SurveyResponseJpaRepository : JpaRepository<SurveyResponseEntity, Long> {
    fun findAllBySurveyId(surveyId: Long): List<SurveyResponseEntity>
}

@Repository
class SurveyResponseRepositoryImpl(
    private val surveyResponseJpaRepository: SurveyResponseJpaRepository,
) : SurveyResponseRepository {
    override fun save(surveyResponse: SurveyResponse): Long {
        val entity =
            SurveyResponseEntity(
                surveyId = surveyResponse.surveyId,
            )
        return surveyResponseJpaRepository.save(entity).id
    }

    override fun findById(id: Long): SurveyResponse? {
        val entity = surveyResponseJpaRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("설문조사 응답이 존재하지 않습니다.")

        return SurveyResponse(
            id = entity.id,
            surveyId = entity.surveyId,
            createdAt = entity.createdAt,
        )
    }

    override fun findAllBySurveyId(surveyId: Long): List<SurveyResponse> {
        val entities = surveyResponseJpaRepository.findAllBySurveyId(surveyId)

        if (entities.isEmpty()) {
            return emptyList()
        }

        return entities.map { entity ->
            SurveyResponse(
                id = entity.id,
                surveyId = entity.surveyId,
                createdAt = entity.createdAt,
            )
        }
    }
}

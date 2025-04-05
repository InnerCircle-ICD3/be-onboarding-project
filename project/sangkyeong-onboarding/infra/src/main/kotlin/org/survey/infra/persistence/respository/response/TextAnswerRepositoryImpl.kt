package org.survey.infra.persistence.respository.response

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.survey.domain.response.model.TextAnswer
import org.survey.domain.response.repository.TextAnswerRepository
import org.survey.infra.persistence.entity.response.TextAnswerEntity

interface TextAnswerJpaRepository : JpaRepository<TextAnswerEntity, Long> {
    fun findAllBySurveyResponseId(surveyResponseId: Long): List<TextAnswerEntity>

    fun findAllBySurveyResponseIdIn(surveyResponseIds: Collection<Long>): List<TextAnswerEntity>
}

@Repository
class TextAnswerRepositoryImpl(
    private val textAnswerJpaRepository: TextAnswerJpaRepository,
) : TextAnswerRepository {
    override fun saveAll(textAnswer: List<TextAnswer>) {
        val entities =
            textAnswer.map { answer ->
                TextAnswerEntity(
                    surveyResponseId = answer.surveyResponseId,
                    surveyItemId = answer.surveyItemId,
                    value = answer.value,
                )
            }
        textAnswerJpaRepository.saveAll(entities)
    }

    override fun findAllBySurveyResponseId(surveyResponseId: Long): List<TextAnswer> {
        return textAnswerJpaRepository.findAllBySurveyResponseId(surveyResponseId)
            .map { entity ->
                TextAnswer(
                    surveyResponseId = entity.surveyResponseId,
                    surveyItemId = entity.surveyItemId,
                    value = entity.value,
                )
            }
    }

    override fun findAllBySurveyResponseIds(surveyResponseIds: Collection<Long>): List<TextAnswer> {
        return textAnswerJpaRepository.findAllBySurveyResponseIdIn(surveyResponseIds)
            .map { entity ->
                TextAnswer(
                    surveyResponseId = entity.surveyResponseId,
                    surveyItemId = entity.surveyItemId,
                    value = entity.value,
                )
            }
    }
}

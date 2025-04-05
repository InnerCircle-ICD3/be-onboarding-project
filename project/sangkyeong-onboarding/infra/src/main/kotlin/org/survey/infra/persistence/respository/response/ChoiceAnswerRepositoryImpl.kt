package org.survey.infra.persistence.respository.response

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.survey.domain.response.model.ChoiceAnswer
import org.survey.domain.response.repository.ChoiceAnswerRepository
import org.survey.infra.persistence.entity.response.ChoiceAnswerEntity

interface ChoiceAnswerJpaRepository : JpaRepository<ChoiceAnswerEntity, Long> {
    fun findAllBySurveyResponseId(surveyResponseId: Long): List<ChoiceAnswerEntity>

    fun findAllBySurveyResponseIdIn(surveyResponseIds: Collection<Long>): List<ChoiceAnswerEntity>
}

@Repository
class ChoiceAnswerRepositoryImpl(
    private val choiceAnswerJpaRepository: ChoiceAnswerJpaRepository,
) : ChoiceAnswerRepository {
    override fun saveAll(choiceAnswer: List<ChoiceAnswer>) {
        val entities =
            choiceAnswer.map { answer ->
                ChoiceAnswerEntity(
                    surveyResponseId = answer.surveyResponseId,
                    surveyItemId = answer.surveyItemId,
                    itemOptionIds = answer.itemOptionIds.toMutableSet(),
                )
            }
        choiceAnswerJpaRepository.saveAll(entities)
    }

    override fun findAllBySurveyResponseId(surveyResponseId: Long): List<ChoiceAnswer> {
        return choiceAnswerJpaRepository.findAllBySurveyResponseId(surveyResponseId)
            .map { entity ->
                ChoiceAnswer(
                    surveyResponseId = entity.surveyResponseId,
                    surveyItemId = entity.surveyItemId,
                    itemOptionIds = entity.itemOptionIds.toSet(),
                )
            }
    }

    override fun findAllBySurveyResponseIds(surveyResponseIds: Collection<Long>): List<ChoiceAnswer> {
        return choiceAnswerJpaRepository.findAllBySurveyResponseIdIn(surveyResponseIds)
            .map { entity ->
                ChoiceAnswer(
                    surveyResponseId = entity.surveyResponseId,
                    surveyItemId = entity.surveyItemId,
                    itemOptionIds = entity.itemOptionIds.toSet(),
                )
            }
    }
}

package com.innercircle.domain.survey.command.service

import com.innercircle.common.CommandService
import com.innercircle.domain.survey.command.dto.SurveyAnswerCreateCommand
import com.innercircle.domain.survey.repository.SurveyAnswerRepository
import com.innercircle.survey.entity.SurveyAnswer
import com.innercircle.survey.entity.SurveyQuestion
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

@CommandService
class SurveyAnswerCommandService(
    @PersistenceContext
    private val entityManager: EntityManager,
    private val surveyAnswerRepository: SurveyAnswerRepository
) {
    fun create(surveyQuestionId: Long, command: SurveyAnswerCreateCommand): SurveyAnswer {
        val surveyQuestion = entityManager.find(SurveyQuestion::class.java, surveyQuestionId)
        val surveyAnswer = SurveyAnswer.of(surveyQuestion, command)

        return surveyAnswerRepository.save(surveyAnswer)
    }

}
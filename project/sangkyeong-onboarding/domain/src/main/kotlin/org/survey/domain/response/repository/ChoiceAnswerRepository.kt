package org.survey.domain.response.repository

import org.survey.domain.response.model.ChoiceAnswer

interface ChoiceAnswerRepository {
    fun saveAll(choiceAnswer: List<ChoiceAnswer>)

    fun findAllBySurveyResponseId(surveyResponseId: Long): List<ChoiceAnswer>

    fun findAllBySurveyResponseIds(surveyResponseIds: Collection<Long>): List<ChoiceAnswer>
}

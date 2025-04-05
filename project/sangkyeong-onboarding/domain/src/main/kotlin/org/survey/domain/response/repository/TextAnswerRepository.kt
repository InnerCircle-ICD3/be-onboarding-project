package org.survey.domain.response.repository

import org.survey.domain.response.model.TextAnswer

interface TextAnswerRepository {
    fun saveAll(textAnswer: List<TextAnswer>)

    fun findAllBySurveyResponseId(surveyResponseId: Long): List<TextAnswer>

    fun findAllBySurveyResponseIds(surveyResponseIds: Collection<Long>): List<TextAnswer>
}

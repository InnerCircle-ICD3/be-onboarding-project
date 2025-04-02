package com.innercircle.domain.survey.query.service

import com.innercircle.common.QueryService
import com.innercircle.domain.survey.repository.SurveyRepository
import com.innercircle.survey.entity.Survey
import lombok.RequiredArgsConstructor
import java.util.*

@QueryService
@RequiredArgsConstructor
class SurveyQueryService(
    private val surveyRepository: SurveyRepository
) {
    fun fetchSurveyAggregateOrThrow(id: UUID): Survey {
        val surveyWithQuestions = surveyRepository.fetchSurveyQuestions(id).orElseThrow()
        val surveyWithAnswers = surveyRepository.fetchSurveyAnswers(id).orElseThrow()
        surveyWithQuestions.answers.addAll(surveyWithAnswers.answers)
        return surveyWithQuestions
    }

}
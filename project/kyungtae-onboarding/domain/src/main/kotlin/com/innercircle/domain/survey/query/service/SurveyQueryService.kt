package com.innercircle.domain.survey.query.service

import com.innercircle.common.QueryService
import com.innercircle.common.SoftDeletedFilter
import com.innercircle.domain.survey.repository.SurveyRepository
import com.innercircle.survey.entity.Survey
import lombok.RequiredArgsConstructor
import java.util.*

@QueryService
@RequiredArgsConstructor
class SurveyQueryService(
    private val surveyRepository: SurveyRepository
) {
    @SoftDeletedFilter
    fun fetchSurveyAggregateOrThrow(id: UUID): Survey = surveyRepository.fetchSurveyQuestions(id).orElseThrow()

}
package org.survey.taehwanonboarding.domain.repository.survey

import org.springframework.data.jpa.repository.JpaRepository
import org.survey.taehwanonboarding.domain.entity.response.SurveyResponse

interface SurveyResponseRepository : JpaRepository<SurveyResponse, Long> {
    fun findAllBySurveyId(surveyId: Long): List<SurveyResponse>
}
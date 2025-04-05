package org.survey.taehwanonboarding.domain.repository.survey

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.survey.taehwanonboarding.domain.entity.survey.Survey

@Repository
interface SurveyRepository : JpaRepository<Survey, Long> {
    fun findAllByStatus(status: Survey.SurveyStatus): List<Survey>
    fun findAllByStatusNot(status: Survey.SurveyStatus): List<Survey>
}
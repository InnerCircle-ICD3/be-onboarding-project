package com.onboarding.form.repository

import com.onboarding.form.domain.SurveyVersion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SurveyVersionRepository : JpaRepository<SurveyVersion, Long> {
    @Query(
        """
        SELECT sv FROM SurveyVersion sv 
        JOIN FETCH sv.questions 
        WHERE sv.version = :version AND sv.survey.id = :surveyId
        """
    )
    fun findBySurveyIdAndVersion(
        @Param("surveyId") surveyId: Long,
        @Param("version") version: Int,
    ): SurveyVersion?
}
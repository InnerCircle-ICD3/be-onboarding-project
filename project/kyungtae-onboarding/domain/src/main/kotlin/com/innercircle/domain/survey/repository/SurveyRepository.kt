package com.innercircle.domain.survey.repository

import com.innercircle.survey.entity.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface SurveyRepository : JpaRepository<Survey, Long> {

    @Query("SELECT s FROM Survey s join fetch SurveyQuestion sq on sq.survey = s WHERE s.externalId = :id")
    fun fetchSurveyQuestions(id: UUID): Optional<Survey>

    @Query("SELECT s FROM Survey s left join fetch SurveyAnswer sa on sa.survey = s WHERE s.externalId = :id")
    fun fetchSurveyAnswers(id: UUID): Optional<Survey>

}
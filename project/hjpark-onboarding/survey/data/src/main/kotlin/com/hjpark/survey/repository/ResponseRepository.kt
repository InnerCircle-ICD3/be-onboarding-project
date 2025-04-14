package com.hjpark.survey.repository

import com.hjpark.survey.entity.ResponseItem
import com.hjpark.survey.entity.SurveyResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ResponseRepository : JpaRepository<SurveyResponse, Long> {
    fun findBySurveyId(surveyId: Long): List<SurveyResponse>
    fun findBySurveyIdAndRespondentId(surveyId: Long, respondentId: String): List<SurveyResponse>
}

interface ResponseItemRepository : JpaRepository<ResponseItem, Long> {
    fun findBySurveyResponseId(surveyResponseId: Long): List<ResponseItem>

    @Query("""
        SELECT i FROM ResponseItem i 
        WHERE i.surveyResponse.survey.id = :surveyId 
        AND i.questionId = :questionId 
    """)
    fun findBySurveyIdAndQuestionId(
        @Param("surveyId") surveyId: Long,
        @Param("questionId") questionId: Long
    ): List<ResponseItem>
}
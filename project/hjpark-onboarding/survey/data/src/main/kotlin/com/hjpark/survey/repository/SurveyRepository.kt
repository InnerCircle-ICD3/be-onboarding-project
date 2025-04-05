package com.hjpark.survey.repository

import com.hjpark.survey.entity.ResponseItem
import com.hjpark.survey.entity.Survey
import com.hjpark.survey.entity.SurveyResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SurveyRepository : JpaRepository<Survey, Long> {
    @Query("SELECT s FROM Survey s WHERE s.name LIKE %:keyword% ORDER BY s.createTime DESC")
    fun findByNameContaining(@Param("keyword") keyword: String): List<Survey>

    @Query("SELECT s FROM Survey s ORDER BY s.createTime DESC")
    fun findAllOrderByCreateTimeDesc(): List<Survey>
}

interface SurveyResponseRepository : JpaRepository<SurveyResponse, Long> {
    fun findBySurveyId(surveyId: Long): List<SurveyResponse>
    
    @Query("""
     SELECT DISTINCT r FROM SurveyResponse r 
        JOIN FETCH r.items i 
        LEFT JOIN FETCH i.option o 
        WHERE r.survey.id = :surveyId 
        AND (:questionName IS NULL OR i.questionId IN (SELECT q.id FROM Question q WHERE q.name = :questionName))
        AND (:responseValue IS NULL OR i.textValue = :responseValue OR o.text = :responseValue)
     """)
    fun findByQuestionNameAndResponseValue(
        @Param("surveyId") surveyId: Long,
        @Param("questionName") questionName: String?,
        @Param("responseValue") responseValue: String?
    ): List<SurveyResponse>
}
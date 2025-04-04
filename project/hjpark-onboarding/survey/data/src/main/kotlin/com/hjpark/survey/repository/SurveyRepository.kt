package com.hjpark.survey.repository

import com.hjpark.survey.entity.ResponseItem
import com.hjpark.survey.entity.Survey
import com.hjpark.survey.entity.SurveyResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SurveyRepository : JpaRepository<Survey, Long> {
    @Query("SELECT s FROM Survey s WHERE s.name LIKE %:keyword%")
    fun findByNameContaining(@Param("keyword") keyword: String): List<Survey>
}

interface SurveyResponseRepository : JpaRepository<SurveyResponse, Long> {
    fun findBySurveyId(surveyId: Long): List<SurveyResponse>
    
    @Query("""
        SELECT DISTINCT r FROM SurveyResponse r 
        JOIN r.items i 
        WHERE r.survey.id = :surveyId 
//        AND (:questionName IS NULL OR i.question.name = :questionName)
        AND (:responseValue IS NULL OR i.textValue = :responseValue OR i.option.text = :responseValue)
    """)
    fun findByQuestionNameAndResponseValue(
        @Param("surveyId") surveyId: Long,
        @Param("questionName") questionName: String?,
        @Param("responseValue") responseValue: String?
    ): List<SurveyResponse>
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
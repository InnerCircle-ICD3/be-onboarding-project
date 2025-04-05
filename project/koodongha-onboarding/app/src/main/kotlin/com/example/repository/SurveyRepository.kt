package com.example.repository

import com.example.entity.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SurveyRepository : JpaRepository<Survey, Long> {

    @Query("""
        SELECT DISTINCT s FROM Survey s
        LEFT JOIN FETCH s.items i
        LEFT JOIN FETCH i.answers a
        LEFT JOIN FETCH i.options o
        WHERE s.id = :surveyId
    """)
    fun findSurveyWithItemsAndAnswers(@Param("surveyId") surveyId: Long): Survey?
}
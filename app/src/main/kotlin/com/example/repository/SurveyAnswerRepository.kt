package com.example.repository

import com.example.entity.*
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyAnswerRepository : JpaRepository<SurveyAnswer, Long> {
    fun findBySurveyId(surveyId: Long): List<SurveyAnswer>
}

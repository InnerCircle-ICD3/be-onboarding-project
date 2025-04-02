package com.example.repository

import com.example.entity.SurveyAnswerBase
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyAnswerRepository : JpaRepository<SurveyAnswerBase, Long> {
    fun findBySurveyId(surveyId: Long): List<SurveyAnswerBase>
}

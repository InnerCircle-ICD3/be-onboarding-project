package com.hjpark.survey.repository

import com.hjpark.survey.entity.Question
import com.hjpark.survey.entity.QuestionOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface QuestionRepository : JpaRepository<Question, Long> {
    fun findBySurveyId(surveyId: Long): List<Question>

    @Query("SELECT q FROM Question q WHERE q.survey.id = :surveyId ORDER BY q.sequence")
    fun findAllBySurveyIdOrderBySequence(@Param("surveyId") surveyId: Long): List<Question>
}

interface QuestionOptionRepository : JpaRepository<QuestionOption, Long> {
    fun findByQuestionId(questionId: Long): List<QuestionOption>

    fun findAllByQuestionIdOrderBySequence(questionId: Long): List<QuestionOption>
}
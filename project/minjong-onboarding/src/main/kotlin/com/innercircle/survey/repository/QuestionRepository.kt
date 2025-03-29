// src/main/kotlin/com/innercircle/survey/repository/QuestionRepository.kt
package com.innercircle.survey.repository

import com.innercircle.survey.domain.Question
import com.innercircle.survey.domain.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : JpaRepository<Question, Long> {
    // 특정 설문에 속한 질문들을 찾는 메서드
    fun findBySurveyOrderByPositionAsc(survey: Survey): List<Question>
    
    // 특정 설문에 속하고 삭제되지 않은 질문들을 찾는 메서드
    fun findBySurveyAndDeletedFalseOrderByPositionAsc(survey: Survey): List<Question>
}
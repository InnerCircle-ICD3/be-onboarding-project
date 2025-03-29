// src/main/kotlin/com/innercircle/survey/repository/ResponseItemRepository.kt
package com.innercircle.survey.repository

import com.innercircle.survey.domain.Question
import com.innercircle.survey.domain.Response
import com.innercircle.survey.domain.ResponseItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ResponseItemRepository : JpaRepository<ResponseItem, Long> {
    // 특정 응답에 포함된 응답 항목들을 찾는 메서드
    fun findByResponse(response: Response): List<ResponseItem>
    
    // 특정 질문에 대한 모든 응답 항목들을 찾는 메서드
    fun findByQuestion(question: Question): List<ResponseItem>
    
    // Advanced: 항목 이름과 응답 값으로 검색하는 메서드
    @Query("SELECT ri FROM ResponseItem ri JOIN ri.question q JOIN ri.response r JOIN r.survey s " +
           "WHERE s.id = :surveyId AND q.questionName LIKE %:keyword% OR ri.answerValue LIKE %:keyword%")
    fun searchByKeyword(@Param("surveyId") surveyId: Long, @Param("keyword") keyword: String): List<ResponseItem>
}
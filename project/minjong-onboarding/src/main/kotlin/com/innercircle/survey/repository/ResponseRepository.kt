// src/main/kotlin/com/innercircle/survey/repository/ResponseRepository.kt
package com.innercircle.survey.repository

import com.innercircle.survey.domain.Response
import com.innercircle.survey.domain.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ResponseRepository : JpaRepository<Response, Long> {
    // 특정 설문에 대한 모든 응답을 찾는 메서드
    fun findBySurvey(survey: Survey): List<Response>
}
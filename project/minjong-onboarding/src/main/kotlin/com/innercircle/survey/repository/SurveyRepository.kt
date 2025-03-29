// src/main/kotlin/com/innercircle/survey/repository/SurveyRepository.kt
package com.innercircle.survey.repository

import com.innercircle.survey.domain.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyRepository : JpaRepository<Survey, Long> {
    // 기본 CRUD 메서드는 JpaRepository에서 자동 제공됨
}
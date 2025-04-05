// src/main/kotlin/com/innercircle/survey/repository/OptionRepository.kt
package com.innercircle.survey.repository

import com.innercircle.survey.domain.Option
import com.innercircle.survey.domain.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OptionRepository : JpaRepository<Option, Long> {
    // 특정 질문에 속한 옵션들을 찾는 메서드
    fun findByQuestionOrderByPositionAsc(question: Question): List<Option>
}
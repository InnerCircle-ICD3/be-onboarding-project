// src/main/kotlin/com/innercircle/survey/domain/Survey.kt
package com.innercircle.survey.domain

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "surveys")
class Survey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column
    var description: String? = null,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column
    var updatedAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = false)
    val questions: MutableList<Question> = mutableListOf()
) {
    // 질문 추가 메서드
    fun addQuestion(question: Question) {
        questions.add(question)
        question.survey = this
    }
}
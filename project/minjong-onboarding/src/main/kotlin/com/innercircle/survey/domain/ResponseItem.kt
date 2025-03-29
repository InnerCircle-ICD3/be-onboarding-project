// src/main/kotlin/com/innercircle/survey/domain/ResponseItem.kt
package com.innercircle.survey.domain

import jakarta.persistence.*

@Entity
@Table(name = "response_items")
class ResponseItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id")
    var response: Response? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    val question: Question,

    @Column(nullable = false)
    var answerValue: String
)
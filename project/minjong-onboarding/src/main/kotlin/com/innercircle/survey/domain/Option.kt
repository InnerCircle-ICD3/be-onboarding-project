// src/main/kotlin/com/innercircle/survey/domain/Option.kt
package com.innercircle.survey.domain

import jakarta.persistence.*

@Entity
@Table(name = "options")
class Option(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var optionValue: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    var question: Question? = null,

    @Column(nullable = false)
    var position: Int = 0
)
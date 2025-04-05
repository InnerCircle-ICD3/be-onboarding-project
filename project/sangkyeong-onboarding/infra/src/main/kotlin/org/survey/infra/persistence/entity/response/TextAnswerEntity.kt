package org.survey.infra.persistence.entity.response

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "text_answer")
class TextAnswerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    val surveyResponseId: Long,
    @Column(nullable = false)
    val surveyItemId: Long,
    @Column(nullable = false, columnDefinition = "TEXT")
    val value: String,
)

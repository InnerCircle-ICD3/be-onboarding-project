package org.survey.infra.persistence.entity.survey

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "survey")
class SurveyEntity(
    title: String,
    description: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        protected set

    var description: String = description
        protected set

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set
}

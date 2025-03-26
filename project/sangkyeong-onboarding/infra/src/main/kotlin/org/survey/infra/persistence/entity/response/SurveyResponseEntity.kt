package org.survey.infra.persistence.entity.response

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "survey_response")
class SurveyResponseEntity(
    surveyId: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var surveyId: Long = surveyId
        protected set

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set
}

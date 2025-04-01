package org.survey.infra.persistence.entity.response

import jakarta.persistence.*

@Entity
@Table(name = "answer")
class AnswerEntity(
    surveyResponseId: Long,
    surveyItemId: Long,
    value: String? = null,
    itemOptionId: Long? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var surveyResponseId: Long = surveyResponseId
        protected set

    var surveyItemId: Long = surveyItemId
        protected set

    var value: String? = value
        protected set

    var itemOptionId: Long? = itemOptionId
        protected set
}

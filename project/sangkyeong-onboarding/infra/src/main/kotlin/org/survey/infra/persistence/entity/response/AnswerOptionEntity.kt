package org.survey.infra.persistence.entity.response

import jakarta.persistence.*

@Entity
@Table(name = "answer_option")
class AnswerOptionEntity(
    answerId: Long,
    itemOptionId: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var answerId: Long = answerId
        protected set

    var itemOptionId: Long = itemOptionId
        protected set
}

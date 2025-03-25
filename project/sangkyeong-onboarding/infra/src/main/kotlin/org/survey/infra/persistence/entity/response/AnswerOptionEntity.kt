package org.survey.infra.persistence.entity.response

import jakarta.persistence.*

@Entity
@Table(name = "answer_option")
class AnswerOptionEntity(
    responseAnswerId: Long,
    itemOptionId: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var responseAnswerId: Long = responseAnswerId
        protected set

    var itemOptionId: Long = itemOptionId
        protected set
}

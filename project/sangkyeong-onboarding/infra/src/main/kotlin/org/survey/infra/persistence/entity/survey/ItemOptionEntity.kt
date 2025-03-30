package org.survey.infra.persistence.entity.survey

import jakarta.persistence.*

@Entity
@Table(name = "item_option")
class ItemOptionEntity(
    surveyItemId: Long,
    value: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var surveyItemId: Long = surveyItemId
        protected set

    var value: String = value
        protected set
}
package org.survey.infra.persistence.entity.survey

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

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

    var isDeleted: Boolean = false
        protected set
}

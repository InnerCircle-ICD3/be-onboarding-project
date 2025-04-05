package org.survey.infra.persistence.entity.survey

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "survey_item")
class SurveyItemEntity(
    surveyId: Long,
    title: String,
    description: String?,
    inputType: String,
    isRequired: Boolean,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var surveyId: Long = surveyId
        protected set

    var title: String = title
        protected set

    var description: String? = description
        protected set

    var inputType: String = inputType
        protected set

    var isRequired: Boolean = isRequired
        protected set

    var isDeleted: Boolean = false
        protected set
}

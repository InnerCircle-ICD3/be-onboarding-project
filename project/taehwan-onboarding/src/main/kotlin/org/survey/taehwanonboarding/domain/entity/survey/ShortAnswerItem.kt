package org.survey.taehwanonboarding.domain.entity.survey

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("SHORT_ANSWER")
class ShortAnswerItem(
    title: String,
    description: String? = null,
    required: Boolean = false,
    orderNumber: Int = 0,

    @Column
    var maxLength: Int? = 255,
) : SurveyItem(
    title = title,
    description = description,
    required = required,
    orderNumber = orderNumber,
) {
    override fun validateResponse(responseValue: String?): Boolean {
        if (required && responseValue.isNullOrBlank()) return false
        return responseValue == null ||
            (maxLength == null || responseValue.length <= maxLength!!)
    }
}
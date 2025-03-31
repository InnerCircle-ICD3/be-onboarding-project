package org.survey.taehwanonboarding.domain.survey

import jakarta.persistence.CollectionTable
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OrderColumn

@Entity
@DiscriminatorValue("SINGLE_SELECTION")
class SingleSelectionItem(
    title: String,
    description: String? = null,
    required: Boolean = false,
    orderNumber: Int = 0,
    @ElementCollection
    @CollectionTable(
        name = "survey_item_options",
        joinColumns = [JoinColumn(name = "survey_item_id")],
    )
    @OrderColumn(name = "order")
    var options: MutableList<String> = mutableListOf(),
) : SurveyItem(
        title = title,
        description = description,
        required = required,
        orderNumber = orderNumber,
    ) {
    override fun validateResponse(responseValue: String?): Boolean {
        if (required && responseValue.isNullOrBlank()) return false
        return responseValue == null || options.contains(responseValue)
    }
}
package org.survey.taehwanonboarding.domain.entity.survey

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OrderColumn

@Entity
@DiscriminatorValue("MULTI_SELECTION")
class MultiSelectionItem(
    title: String,
    description: String? = null,
    required: Boolean = false,
    orderNumber: Int = 0,

    @ElementCollection
    @CollectionTable(
        name = "item_option",
        joinColumns = [JoinColumn(name = "survey_item_id")],
    )
    @OrderColumn(name = "order")
    var options: MutableList<String> = mutableListOf(),

    @Column
    var minSelections: Int? = null,

    @Column
    var maxSelections: Int? = null,
) : SurveyItem(
    title = title,
    description = description,
    required = required,
    orderNumber = orderNumber,
) {
    override fun validateResponse(responseValue: String?): Boolean {
        if (responseValue.isNullOrBlank()) {
            return !required
        }

        val selectedOptions = responseValue.split(",").map { it.trim() }

        // 선택한 옵션 검증
        if (!selectedOptions.all { options.contains(it) }) {
            return false
        }

        val count = selectedOptions.size
        if (required && count == 0) return false
        if (minSelections != null && count < minSelections!!) return false
        if (maxSelections != null && count > maxSelections!!) return false

        return true
    }
}
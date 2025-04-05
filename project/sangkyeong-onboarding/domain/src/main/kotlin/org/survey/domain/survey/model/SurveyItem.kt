package org.survey.domain.survey.model

class SurveyItem(
    val id: Long = 0,
    val surveyId: Long,
    var title: String,
    var description: String?,
    var inputType: String,
    var isRequired: Boolean,
    var isDeleted: Boolean = false,
    val options: List<String>? = null,
) {
    fun markAsDeleted() {
        if (isDeleted) {
            throw IllegalStateException("설문 항목이 이미 삭제되었습니다.")
        }
        this.isDeleted = true
    }

    fun update(
        title: String,
        description: String?,
        inputType: String,
        isRequired: Boolean,
    ) {
        this.title = title
        this.description = description
        this.inputType = inputType
        this.isRequired = isRequired
    }
}

package org.survey.domain.survey.model

class ItemOption(
    val id: Long = 0,
    val surveyItemId: Long,
    var value: String,
    var isDeleted: Boolean = false,
) {
    fun markAsDeleted() {
        if (isDeleted) {
            throw IllegalStateException("해당 옵션이 이미 삭제되었습니다.")
        }
        this.isDeleted = true
    }
}

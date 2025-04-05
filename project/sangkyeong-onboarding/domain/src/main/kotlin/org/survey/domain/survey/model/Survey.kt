package org.survey.domain.survey.model

import java.time.LocalDateTime

class Survey(
    val id: Long = 0,
    var title: String,
    var description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    fun update(
        title: String,
        description: String,
    ) {
        this.title = title
        this.description = description
    }
}

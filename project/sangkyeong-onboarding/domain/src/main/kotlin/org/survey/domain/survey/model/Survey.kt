package org.survey.domain.survey.model

import java.time.LocalDateTime

class Survey(
    val id: Long = 0,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)

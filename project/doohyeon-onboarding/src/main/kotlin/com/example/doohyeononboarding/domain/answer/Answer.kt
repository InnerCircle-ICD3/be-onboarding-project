package com.example.doohyeononboarding.domain.answer

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * 응답
 */
@Entity
class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val answerId: Long = 0,
) {
}

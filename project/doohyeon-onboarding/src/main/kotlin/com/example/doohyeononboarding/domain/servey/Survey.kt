package com.example.doohyeononboarding.domain.servey

import com.example.doohyeononboarding.domain.BaseEntity
import com.example.doohyeononboarding.domain.question.Question
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

/**
 * 설문조사
 */
@Entity
class Survey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val surveyId: Long = 0,

    var title: String,

    var description: String? = "",

    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    var questions: List<Question>? = mutableListOf(),
) : BaseEntity() {

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateDescription(description: String) {
        this.description = description
    }
    
}

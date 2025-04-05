package com.example.doohyeononboarding.domain.question

import com.example.doohyeononboarding.domain.BaseEntity
import com.example.doohyeononboarding.domain.servey.Survey
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * 설문조사 항목
 */
@Entity
class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val questionId: Long = 0,

    val title: String,

    val description: String? = "",

    @Enumerated(EnumType.STRING)
    val type: QuestionType,

    val isRequired: Boolean,

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = [JoinColumn(name = "question_id")])
    @Column(name = "option_text")
    val options: MutableList<String> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    val survey: Survey
) : BaseEntity() {

}

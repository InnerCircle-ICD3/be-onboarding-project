package com.onboarding.form.domain

import jakarta.persistence.*

@Entity
@Table(name = "question_versions")
class SurveyVersion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var version: Int,
    @ManyToOne
    @JoinColumn(name = "survey_id")
    var survey: Survey? = null,
    @OneToMany(mappedBy = "surveyVersion", cascade = [CascadeType.ALL])
    val questions: MutableList<Question> = mutableListOf()
) {
    fun addQuestion(question: Question) {
        question.surveyVersion = this
        this.questions.add(question)
    }
}
package com.onboarding.form.domain

import jakarta.persistence.*

@Entity
@Table(name = "question_versions")
class SurveyVersion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var version: Int,
    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,
    @OneToMany(mappedBy = "surveyVersion", cascade = [CascadeType.ALL])
    val questions: MutableList<Question> = mutableListOf()
) {
    fun addQuestion(question: Question) {
        question.surveyVersion = this
        this.questions.add(question)
    }

    companion object {
        fun of(version: Int, survey: Survey) = SurveyVersion(0, version, survey)
    }
}
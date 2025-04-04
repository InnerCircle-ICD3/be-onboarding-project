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
    var survey: Survey? = null,
    @OneToMany(mappedBy = "surveyVersion", cascade = [CascadeType.ALL])
    val questions: MutableList<Question> = mutableListOf()
) {
    fun addQuestion(question: Question) {
        question.surveyVersion = this
        this.questions.add(question)
    }

    fun checkValid(questionIdToResponse: Map<Long, Answer>) {
        questions.forEach { question ->
            if (!question.isRequired) return@forEach
            val answer =
                requireNotNull(questionIdToResponse.getValue(question.id)) { "QuestionId ${question.id} is answer required" }
            question.isValidAnswer(answer)
        }
    }

    companion object {
        fun of(version: Int, survey: Survey) = SurveyVersion(0, version, survey)
    }
}
package com.onboarding.form.domain

import jakarta.persistence.*


@Entity
class Survey(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var description: String,
    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL])
    @OrderBy("version desc")
    val versions: MutableList<SurveyVersion> = mutableListOf(),
) {
    fun getCurrentVersion(): SurveyVersion = requireNotNull(versions.firstOrNull())

    fun getQuestions(): List<Question> = requireNotNull(versions.firstOrNull()).questions

    fun addQuestion(question: Question) {
        check(getCurrentVersion().questions.size < MAX_QUESTION_SIZE) { "The number of questions cannot exceed $MAX_QUESTION_SIZE" }
        this.getCurrentVersion().addQuestion(question)
    }

    fun update(newTitle: String, newDescription: String, newQuestions: List<Question>) {
        this.title = newTitle
        this.description = newDescription

        val newVersion = SurveyVersion(
            version = getCurrentVersion().version + 1,
            survey = this
        )
        newQuestions.forEach { newVersion.addQuestion(it) }

        versions.addFirst(newVersion)
    }

    companion object {
        private const val MAX_QUESTION_SIZE = 10

        fun of(title: String, description: String): Survey {
            val survey = Survey(
                title = title,
                description = description
            )
            survey.versions.add(SurveyVersion.of(0, survey))

            return survey
        }
    }
}
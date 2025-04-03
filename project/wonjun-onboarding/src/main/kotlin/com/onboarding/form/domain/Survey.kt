package com.onboarding.form.domain

import jakarta.persistence.*


@Entity
class Survey(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var description: String
) {
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "current_version_id")
    lateinit var currentVersion: SurveyVersion

    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL])
    val versionHistory: MutableList<SurveyVersion> = mutableListOf()

    fun getQuestions() = currentVersion.questions

    fun addQuestion(question: Question) {
        check(currentVersion.questions.size < MAX_QUESTION_SIZE) { "The number of questions cannot exceed $MAX_QUESTION_SIZE" }
        this.currentVersion.addQuestion(question)
    }

    fun update(newTitle: String, newDescription: String, newQuestions: List<Question>) {
        versionHistory.add(currentVersion)

        this.title = newTitle
        this.description = newDescription

        val newVersion = SurveyVersion(
            version = currentVersion.version + 1,
            survey = this
        )
        currentVersion = newVersion
        newQuestions.forEach { addQuestion(it) }
    }

    companion object {
        private const val MAX_QUESTION_SIZE = 10

        fun of(title: String, description: String): Survey {
            val survey = Survey(
                title = title,
                description = description
            )
            survey.currentVersion = SurveyVersion.of(0, survey)

            return survey
        }
    }
}
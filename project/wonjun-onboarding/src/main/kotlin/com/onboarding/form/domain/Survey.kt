package com.onboarding.form.domain

import jakarta.persistence.*


@Entity
class Survey (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val description: String,
    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    val questions: MutableList<Question> = mutableListOf()
){
    fun addQuestion(question: Question){
        check(questions.size < MAX_QUESTION_SIZE) { "The number of questions cannot exceed $MAX_QUESTION_SIZE" }
        question.survey = this
        this.questions.add(question)
    }

    companion object{
        private const val MAX_QUESTION_SIZE = 10
    }
}
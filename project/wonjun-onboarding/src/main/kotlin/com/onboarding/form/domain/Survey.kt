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
    fun addItem(question: Question){
        question.survey = this
        this.questions.add(question)
    }
}
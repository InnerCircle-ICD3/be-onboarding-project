package com.hjpark.survey.domain.model

import java.time.LocalDateTime

data class Survey(
    val id: Long? = null,
    val title: String,
    val description: String?,
    val questions: MutableList<Question> = mutableListOf()
) {
    val createTime: LocalDateTime = LocalDateTime.now()
    var updateTime: LocalDateTime = LocalDateTime.now()

    fun addQuestion(question: Question) {
        require(questions.size < 10) { "설문 문항은 최대 10개까지만 추가할 수 있습니다." }
        questions.add(question)
    }

    fun removeQuestion(question: Question) {
        require(questions.size > 1) { "설문 문항은 최소 1개 이상이어야 합니다." }
        questions.remove(question)
    }

    fun updateQuestion(updatedQuestion: Question) {
        val index = questions.indexOfFirst { it.id == updatedQuestion.id }
        if (index != -1) {
            questions[index] = updatedQuestion
        }
    }

    fun validateQuestionCount(): Boolean {
        return questions.size in 1..10
    }
} 
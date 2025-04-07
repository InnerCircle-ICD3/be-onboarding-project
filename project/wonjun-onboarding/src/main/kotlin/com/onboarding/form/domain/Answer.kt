package com.onboarding.form.domain

import CreateAnswerDto
import CreateInsertAnswerDto
import CreateSelectAnswerDto
import jakarta.persistence.*


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "type",
    discriminatorType = DiscriminatorType.STRING
)
@Table(name = "answer")
abstract class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0,
    @Column(name = "question_id", nullable = false)
    open val questionId: Long,
) {
    companion object {
        fun of(answerDto: CreateAnswerDto) = when (answerDto) {
            is CreateInsertAnswerDto -> InsertAnswer.of(answerDto)
            is CreateSelectAnswerDto -> SelectAnswer.of(answerDto)
            else -> throw IllegalArgumentException("is not valid response type")
        }
    }
}

@Entity
@DiscriminatorValue("insert")
class InsertAnswer(
    id: Long = 0,
    questionId: Long,
    val content: String,
) : Answer(id, questionId) {
    companion object {
        fun of(answerDto: CreateInsertAnswerDto) = InsertAnswer(0, answerDto.questionId, answerDto.content)
    }
}

@Entity
@DiscriminatorValue("select")
class SelectAnswer(
    id: Long = 0,
    questionId: Long,
    @ElementCollection
    @CollectionTable
    val selected: List<String>,
) : Answer(id, questionId) {
    companion object {
        fun of(answerDto: CreateSelectAnswerDto) = SelectAnswer(0, answerDto.questionId, answerDto.selected)
    }
}
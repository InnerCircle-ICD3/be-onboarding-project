package com.onboarding.form.domain

import jakarta.persistence.*


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "type",
    discriminatorType = DiscriminatorType.STRING
)
@Table(name = "question")
abstract class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val description: String,
    val isRequired: Boolean,
    @ManyToOne
    @JoinColumn(name = "survey_id")
    var survey: Survey? = null
) {
    abstract fun getType(): QuestionType
}


@Entity
@DiscriminatorValue(QuestionType.LONG_VALUE)
class LongQuestion(
    id: Long? = null,
    title: String,
    description: String,
    isRequired: Boolean,
    survey: Survey? = null
) : Question(
    id,
    title,
    description,
    isRequired,
    survey
) {
    override fun getType(): QuestionType = QuestionType.LONG
}

@Entity
@DiscriminatorValue(QuestionType.SHORT_VALUE)
class ShortQuestion(
    id: Long? = null,
    title: String,
    description: String,
    isRequired: Boolean,
    survey: Survey? = null
) : Question(
    id,
    title,
    description,
    isRequired,
    survey
) {
    override fun getType(): QuestionType = QuestionType.SHORT
}

@Entity
@DiscriminatorValue(QuestionType.SINGLE_SELECT_VALUE)
class SingleSelectQuestion(
    id: Long? = null,
    title: String,
    description: String,
    isRequired: Boolean,
    @ElementCollection
    @CollectionTable
    val answerList: List<String>,
    survey: Survey? = null
) : Question(
    id,
    title,
    description,
    isRequired,
    survey
) {
    override fun getType(): QuestionType = QuestionType.SINGLE_SELECT
}

@Entity
@DiscriminatorValue(QuestionType.MULTI_SELECT_VALUE)
class MultiSelectQuestion(
    id: Long? = null,
    title: String,
    description: String,
    isRequired: Boolean,
    @ElementCollection
    @CollectionTable
    val answerList: List<String>,
    survey: Survey? = null
) : Question(
    id,
    title,
    description,
    isRequired,
    survey
) {
    override fun getType(): QuestionType = QuestionType.MULTI_SELECT
}
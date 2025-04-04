package com.onboarding.form.domain

import com.onboarding.form.request.CreateQuestionDto
import com.onboarding.form.request.CreateSelectQuestionDto
import com.onboarding.form.request.CreateStandardQuestionDto
import jakarta.persistence.*


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "question_type",
    discriminatorType = DiscriminatorType.STRING
)
@Table(name = "questions")
abstract class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0,
    open val title: String,
    open val description: String,
    open val isRequired: Boolean,
    @ManyToOne
    @JoinColumn(name = "survey_version_id")
    open var surveyVersion: SurveyVersion? = null
) {
    @get:Transient
    abstract val type: QuestionType

    abstract fun isValidAnswer(answer: Answer)

    companion object {
        fun of(question: CreateQuestionDto): Question {
            return when (question) {
                is CreateStandardQuestionDto -> when (question.type) {
                    QuestionType.SHORT -> ShortQuestion(0, question.title, question.description, question.isRequired)
                    QuestionType.LONG -> LongQuestion(0, question.title, question.description, question.isRequired)
                    else -> throw IllegalArgumentException("Question type ${question.type} not supported")
                }

                is CreateSelectQuestionDto -> when (question.type) {
                    QuestionType.SINGLE_SELECT -> SingleSelectQuestion(
                        0,
                        question.title,
                        question.description,
                        question.isRequired,
                        question.answerList
                    )

                    QuestionType.MULTI_SELECT -> MultiSelectQuestion(
                        0,
                        question.title,
                        question.description,
                        question.isRequired,
                        question.answerList
                    )

                    else -> throw IllegalArgumentException("Question type ${question.type} not supported")
                }
            }
        }
    }
}


@Entity
@DiscriminatorValue(QuestionType.LONG_VALUE)
class LongQuestion(
    id: Long = 0,
    title: String,
    description: String,
    isRequired: Boolean,
    surveyVersion: SurveyVersion? = null
) : Question(
    id,
    title,
    description,
    isRequired,
    surveyVersion
) {
    override val type: QuestionType = QuestionType.LONG
    override fun isValidAnswer(answer: Answer) {
        check(answer is InsertAnswer) { "invalid AnswerType" }
    }
}

@Entity
@DiscriminatorValue(QuestionType.SHORT_VALUE)
class ShortQuestion(
    id: Long = 0,
    title: String,
    description: String,
    isRequired: Boolean,
    surveyVersion: SurveyVersion? = null
) : Question(
    id,
    title,
    description,
    isRequired,
    surveyVersion
) {
    override val type: QuestionType = QuestionType.SHORT
    override fun isValidAnswer(answer: Answer) {
        check(answer is InsertAnswer) { "invalid AnswerType" }
    }
}

@Entity
@DiscriminatorValue(QuestionType.SINGLE_SELECT_VALUE)
class SingleSelectQuestion(
    id: Long = 0,
    title: String,
    description: String,
    isRequired: Boolean,
    @ElementCollection
    @CollectionTable
    val answerList: List<String>,
    surveyVersion: SurveyVersion? = null
) : Question(
    id,
    title,
    description,
    isRequired,
    surveyVersion
) {
    override val type: QuestionType = QuestionType.SINGLE_SELECT
    override fun isValidAnswer(answer: Answer) {
        check(answer is SelectAnswer) { "invalid AnswerType" }
        require(answer.selected.size == 1) { "Selected Answer is invalid" }
        require(answerList.contains(answer.selected.first())) { "Selected Answer is not in candidates" }
    }
}

@Entity
@DiscriminatorValue(QuestionType.MULTI_SELECT_VALUE)
class MultiSelectQuestion(
    id: Long = 0,
    title: String,
    description: String,
    isRequired: Boolean,
    @ElementCollection
    @CollectionTable
    val answerList: List<String>,
    surveyVersion: SurveyVersion? = null
) : Question(
    id,
    title,
    description,
    isRequired,
    surveyVersion
) {
    override val type: QuestionType = QuestionType.MULTI_SELECT
    override fun isValidAnswer(answer: Answer) {
        check(answer is SelectAnswer) { "invalid AnswerType" }
        answer.selected
            .forEach {
                require(answerList.contains(it)) { "Selected Answer is not in candidates" }
            }
    }
}
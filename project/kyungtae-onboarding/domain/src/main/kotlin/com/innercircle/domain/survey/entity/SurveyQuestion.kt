package com.innercircle.survey.entity

import com.innercircle.common.DateTimeSystemAttribute
import com.innercircle.domain.survey.command.dto.SurveyQuestionCreateCommand
import com.innercircle.domain.survey.entity.SurveyQuestionContext
import jakarta.persistence.*

@Entity
class SurveyQuestion private constructor(

    @JoinColumn(name = "survey_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val survey: Survey,

    @Embedded
    val context: SurveyQuestionContext,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val questionType: QuestionType

) : DateTimeSystemAttribute() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var required: Boolean = true
        set(value) {
            field = value
        }

    @OneToMany(mappedBy = "surveyQuestion", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    val options: MutableList<SurveyQuestionOption> = mutableListOf()

    companion object {

        fun of(survey: Survey, questionCommand: SurveyQuestionCreateCommand): SurveyQuestion {
            val surveyQuestion = SurveyQuestion(
                survey = survey,
                SurveyQuestionContext(
                    name = questionCommand.name,
                    description = questionCommand.description
                ),
                questionType = questionCommand.questionType,
            ).apply {
                survey.questions.add(this)
                this.options.addAll(questionCommand.options.map { SurveyQuestionOption.of(this, it) }.toList())
            }.also {
                validateType(it)
            }
            surveyQuestion.required = questionCommand.required
            return surveyQuestion
        }

        private fun validateType(surveyQuestion: SurveyQuestion) {
            when (surveyQuestion.questionType) {
                QuestionType.SHORT_ANSWER -> check(surveyQuestion.options.isEmpty()) { "Short answer question should not have options" }
                QuestionType.LONG_ANSWER -> check(surveyQuestion.options.isEmpty()) { "Long answer question should not have options" }
                QuestionType.SINGLE_CHOICE -> check(surveyQuestion.options.isNotEmpty()) { "Single choice question should have options" }
                QuestionType.MULTI_CHOICE -> check(surveyQuestion.options.isNotEmpty()) {
                    "Multi choice question should have options"
                }
            }
        }
    }
}

enum class QuestionType {
    SHORT_ANSWER, LONG_ANSWER, SINGLE_CHOICE, MULTI_CHOICE;
}


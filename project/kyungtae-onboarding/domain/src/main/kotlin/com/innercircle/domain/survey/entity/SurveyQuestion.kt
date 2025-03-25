package com.innercircle.survey.entity

import com.innercircle.common.DateTimeSystemAttribute
import com.innercircle.domain.survey.command.dto.SurveyQuestionCreateCommand
import com.innercircle.domain.survey.entity.SurveyQuestionContext
import jakarta.persistence.*

@Entity
@Table(name = "SurveyQuestion")
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

    @Column(nullable = false, columnDefinition = "tinyint(1) DEFAULT 1")
    var required: Boolean = true
        set(value) {
            field = value
        }

    @OneToMany(mappedBy = "surveyQuestion", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    val options: MutableList<SurveyQuestionOption> = mutableListOf()

    init {
        when (this.questionType) {
            QuestionType.SHORT_ANSWER -> check(options.isEmpty()) { "Short answer question should not have options" }
            QuestionType.LONG_ANSWER -> check(options.isEmpty()) { "Long answer question should not have options" }
            QuestionType.SINGLE_CHOICE -> check(options.isNotEmpty()) { "Single choice question should have options" }
            QuestionType.MULTI_CHOICE -> check(options.isNotEmpty()) {
                "Multi choice question should have options"
            }
        }
    }

    companion object {

        fun of(survey: Survey, questionCommand: SurveyQuestionCreateCommand): SurveyQuestion {
            val surveyQuestion = SurveyQuestion(
                survey = survey,
                SurveyQuestionContext(
                    name = questionCommand.name,
                    description = questionCommand.description
                ),
                questionType = questionCommand.inputType,
            ).apply {
                survey.questions.add(this)
                this.options.addAll(questionCommand.options.map { SurveyQuestionOption.of(this, it) }.toList())
            }
            surveyQuestion.required = questionCommand.required
            return surveyQuestion
        }
    }
}

enum class QuestionType {
    SHORT_ANSWER, LONG_ANSWER, SINGLE_CHOICE, MULTI_CHOICE;
}


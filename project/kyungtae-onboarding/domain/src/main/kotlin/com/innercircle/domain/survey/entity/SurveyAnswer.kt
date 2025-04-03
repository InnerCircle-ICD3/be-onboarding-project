package com.innercircle.survey.entity

import com.innercircle.common.BaseEntity
import com.innercircle.domain.survey.entity.SurveyContext
import com.innercircle.domain.survey.entity.SurveyQuestionContext
import jakarta.persistence.*

@Entity
class SurveyAnswer private constructor(

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_id", nullable = false)
    val survey: Survey,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_question_id", nullable = false)
    val surveyQuestion: SurveyQuestion,

    @Column(nullable = false, length = 500)
    val content: String,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "name", column = Column(name = "survey_name")),
        AttributeOverride(name = "description", column = Column(name = "survey_description"))
    )
    val surveyContext: SurveyContext,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "name", column = Column(name = "survey_question_name")),
        AttributeOverride(name = "description", column = Column(name = "survey_question_description"))
    )
    val surveyQuestionContext: SurveyQuestionContext,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val questionType: QuestionType,

    ) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "surveyAnswer", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    val options: MutableList<SurveyAnswerOption> = mutableListOf()

    init {
        require(content.isNotBlank()) { "content must not be blank" }
    }

    companion object {
        fun of(
            surveyQuestion: SurveyQuestion,
            content: String,
            questionType: QuestionType
        ): SurveyAnswer {
            return SurveyAnswer(
                survey = surveyQuestion.survey,
                surveyQuestion = surveyQuestion,
                content = content,
                surveyContext = surveyQuestion.survey.context,
                surveyQuestionContext = surveyQuestion.context,
                questionType = questionType
            ).also {
                validateType(it)
            }
        }

        private fun validateType(surveyAnswer: SurveyAnswer) {
            when (surveyAnswer.questionType) {
                QuestionType.SHORT_ANSWER -> check(surveyAnswer.options.isEmpty()) { "Short answer question should not have options" }
                QuestionType.LONG_ANSWER -> check(surveyAnswer.options.isEmpty()) { "Long answer question should not have options" }
                QuestionType.SINGLE_CHOICE -> check(surveyAnswer.options.isNotEmpty()) { "Single choice question should have options" }
                QuestionType.MULTI_CHOICE -> check(surveyAnswer.options.isNotEmpty()) {
                    "Multi choice question should have options"
                }
            }
        }
    }
}
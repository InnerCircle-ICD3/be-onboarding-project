package com.innercircle.survey.entity

import com.innercircle.common.BaseEntity
import com.innercircle.common.SoftDeleteFilter
import com.innercircle.domain.survey.entity.SurveyContext
import com.innercircle.domain.survey.entity.SurveyQuestionContext
import jakarta.persistence.*

@Entity
@SoftDeleteFilter
@Table(name = "SurveyAnswer")
class SurveyAnswer private constructor(

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
    val inputType: QuestionType,

    ) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "surveyAnswer", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    val options: MutableList<SurveyAnswerOption> = mutableListOf()

    init {
        require(content.isNotBlank()) { "content must not be blank" }
        when (this.surveyQuestion.questionType) {
            QuestionType.SHORT_ANSWER -> check(options.isEmpty()) { "Short answer question should not have options" }
            QuestionType.LONG_ANSWER -> check(options.isEmpty()) { "Long answer question should not have options" }
            QuestionType.SINGLE_CHOICE -> check(options.isNotEmpty()) { "Single choice question should have options" }
            QuestionType.MULTI_CHOICE -> check(options.isNotEmpty()) {
                "Multi choice question should have options"
            }
        }
    }

    companion object {
        fun of(
            surveyQuestion: SurveyQuestion,
            content: String,
            inputType: QuestionType
        ): SurveyAnswer {
            return SurveyAnswer(
                surveyQuestion = surveyQuestion,
                content = content,
                surveyContext = surveyQuestion.survey.context,
                surveyQuestionContext = surveyQuestion.context,
                inputType = inputType
            )
        }
    }
}


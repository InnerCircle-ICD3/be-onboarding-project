package com.innercircle.survey.entity

import jakarta.persistence.*

@Entity
@Table(name = "survey_answer_option")
class SurveyAnswerOption private constructor(

    @EmbeddedId
    val id: SurveyAnswerOptionId,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("surveyQuestionOptionId")
    @JoinColumn(name = "survey_question_option_id", nullable = false)
    val surveyQuestionOption: SurveyQuestionOption,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("surveyAnswerId")
    @JoinColumn(name = "survey_answer_id", nullable = false)
    val surveyAnswer: SurveyAnswer,

    ) {
    companion object {
        fun of(
            surveyQuestionOption: SurveyQuestionOption,
            surveyAnswer: SurveyAnswer
        ): SurveyAnswerOption {
            val id = SurveyAnswerOptionId(
                surveyQuestionOption.id!!,
                surveyAnswer.id!!
            )
            return SurveyAnswerOption(
                id = id,
                surveyQuestionOption = surveyQuestionOption,
                surveyAnswer = surveyAnswer
            )
        }
    }
}


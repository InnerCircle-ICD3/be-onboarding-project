package com.innercircle.survey.entity

import com.innercircle.common.BaseEntity
import com.innercircle.domain.survey.command.dto.SurveyQuestionOptionCreateCommand
import jakarta.persistence.*

@Entity
class SurveyQuestionOption private constructor(

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_question_id", nullable = false)
    val surveyQuestion: SurveyQuestion,

    @Column(nullable = false, length = 100)
    var content: String,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    init {
        require(content.isNotBlank()) { "content must not be blank" }
    }

    companion object {
        fun of(
            surveyQuestion: SurveyQuestion,
            command: SurveyQuestionOptionCreateCommand
        ): SurveyQuestionOption {
            return SurveyQuestionOption(
                surveyQuestion = surveyQuestion,
                content = command.content
            )
        }
    }

}
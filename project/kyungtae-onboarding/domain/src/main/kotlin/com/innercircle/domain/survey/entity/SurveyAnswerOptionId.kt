package com.innercircle.survey.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class SurveyAnswerOptionId(
    var surveyQuestionOptionId: Long?,
    var surveyAnswerId: Long?
) : Serializable


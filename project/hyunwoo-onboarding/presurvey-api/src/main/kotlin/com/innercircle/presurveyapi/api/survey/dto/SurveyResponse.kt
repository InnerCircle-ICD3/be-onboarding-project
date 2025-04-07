package com.innercircle.presurveyapi.api.survey.dto

import com.innercircle.presurveyapi.domain.survey.model.Question
import com.innercircle.presurveyapi.domain.survey.model.QuestionOption
import com.innercircle.presurveyapi.domain.survey.model.QuestionType
import com.innercircle.presurveyapi.domain.survey.model.Survey
import java.util.*

data class SurveyResponse(
    val id: UUID?,
    val title: String,
    val description: String,
    val questions: List<QuestionResponse>
) {
    companion object {
        fun from(survey: Survey): SurveyResponse {
            return SurveyResponse(
                id = survey.id,
                title = survey.title,
                description = survey.description,
                questions = survey.questions.map { QuestionResponse.from(it) }
            )
        }
    }
}

data class QuestionResponse(
    val id: UUID?,
    val title: String,
    val description: String,
    val type: QuestionType,
    val required: Boolean,
    val options: List<OptionResponse> = emptyList()
) {
    companion object {
        fun from(question: Question): QuestionResponse {
            return QuestionResponse(
                id = null,
                title = question.title,
                description = question.description,
                type = question.type,
                required = question.required,
                options = question.options.map { OptionResponse.from(it) }
            )
        }
    }
}

data class OptionResponse(
    val id: UUID,
    val text: String
) {
    companion object {
        fun from(option: QuestionOption): OptionResponse {
            return OptionResponse(
                id = option.id,
                text = option.text
            )
        }
    }
}
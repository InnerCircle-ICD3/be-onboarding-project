package com.onboarding.form.service

import com.onboarding.form.domain.*
import com.onboarding.form.repository.SurveyRepository
import com.onboarding.form.request.CreateSelectQuestionDto
import com.onboarding.form.request.CreateStandardQuestionDto
import com.onboarding.form.request.CreateSurveyDto
import org.springframework.stereotype.Service


@Service
class SurveyService(
    private val surveyRepository: SurveyRepository
) {
    fun createSurvey(surveyDto: CreateSurveyDto): Survey {
        val survey = Survey(
            title = surveyDto.title,
            description = surveyDto.description,
        )

        surveyDto.item.forEach {
            survey.addItem(
                when (it) {
                    is CreateStandardQuestionDto -> when (it.type) {
                        QuestionType.SHORT -> ShortQuestion(null, it.title, it.description, it.isRequired)
                        QuestionType.LONG -> LongQuestion(null, it.title, it.description, it.isRequired)
                        else -> throw IllegalArgumentException("Question type ${it.type} not supported")
                    }
                    is CreateSelectQuestionDto -> when (it.type) {
                        QuestionType.SINGLE_SELECT -> SingleSelectQuestion(
                            null,
                            it.title,
                            it.description,
                            it.isRequired,
                            it.answerList
                        )

                        QuestionType.MULTI_SELECT -> MultiSelectQuestion(
                            null,
                            it.title,
                            it.description,
                            it.isRequired,
                            it.answerList
                        )
                        else -> throw IllegalArgumentException("Question type ${it.type} not supported")
                    }
                }
            )
        }

        surveyRepository.save(survey)
        return survey
    }
}
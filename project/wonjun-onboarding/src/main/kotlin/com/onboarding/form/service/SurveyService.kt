package com.onboarding.form.service

import com.onboarding.form.domain.Item
import com.onboarding.form.domain.Survey
import com.onboarding.form.repository.SurveyRepository
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
                Item(
                    title = it.title,
                    description = it.description,
                    type = it.itemType,
                    isRequired = it.isRequired,
                    options = it.options,
                )
            )
        }

        surveyRepository.save(survey)
        return survey
    }
}
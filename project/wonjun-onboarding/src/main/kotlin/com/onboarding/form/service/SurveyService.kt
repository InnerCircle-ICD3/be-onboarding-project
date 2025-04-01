package com.onboarding.form.service

import com.onboarding.form.domain.Question
import com.onboarding.form.domain.Survey
import com.onboarding.form.repository.SurveyRepository
import com.onboarding.form.request.CreateSurveyDto
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class SurveyService(
    private val surveyRepository: SurveyRepository
) {
    fun createSurvey(surveyDto: CreateSurveyDto): Survey {
        val survey = Survey.of(
            title = surveyDto.title,
            description = surveyDto.description,
        )

        surveyDto.questions.forEach { survey.addQuestion(Question.of(it)) }

        surveyRepository.save(survey)
        return survey
    }

    @Transactional
    fun updateSurveyDto(id: Long, surveyDto: CreateSurveyDto): Survey {
        val survey = requireNotNull(surveyRepository.findByIdOrNull(id)) { "Survey not found" }

        survey.update(surveyDto.title, surveyDto.description, surveyDto.questions.map { Question.of(it) }.toList())

        surveyRepository.save(survey)
        return survey
    }
}
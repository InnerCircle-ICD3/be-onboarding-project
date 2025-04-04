package com.onboarding.form.service

import CreateAnswerRequestDto
import com.onboarding.form.domain.Answer
import com.onboarding.form.domain.Question
import com.onboarding.form.domain.Survey
import com.onboarding.form.repository.AnswerRepository
import com.onboarding.form.repository.SurveyRepository
import com.onboarding.form.repository.SurveyVersionRepository
import com.onboarding.form.request.CreateSurveyDto
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class SurveyService(
    private val surveyRepository: SurveyRepository,
    private val surveyVersionRepository: SurveyVersionRepository,
    private val answerRepository: AnswerRepository,
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
    fun updateSurvey(id: Long, surveyDto: CreateSurveyDto): Survey {
        val survey = requireNotNull(surveyRepository.findByIdOrNull(id)) { "Survey not found" }

        survey.update(surveyDto.title, surveyDto.description, surveyDto.questions.map { Question.of(it) }.toList())

        surveyRepository.save(survey)
        return survey
    }

    @Transactional
    fun submitAnswer(surveyId: Long, answerRequestDto: CreateAnswerRequestDto) {
        val surveyVersion =
            requireNotNull(surveyVersionRepository.findBySurveyIdAndVersion(surveyId, answerRequestDto.version)) {
                "Survey version not found"
            }

        val questionIdToResponse: Map<Long, Answer> =
            answerRequestDto.answers.associate { it.questionId to Answer.of(it) }

        surveyVersion.checkValid(questionIdToResponse)
        answerRepository.saveAll(questionIdToResponse.values)
    }
}
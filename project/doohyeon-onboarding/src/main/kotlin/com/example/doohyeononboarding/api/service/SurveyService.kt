package com.example.doohyeononboarding.api.service

import com.example.doohyeononboarding.api.service.request.CreateSurveyRequest
import com.example.doohyeononboarding.api.service.request.UpdateSurveyRequest
import com.example.doohyeononboarding.api.service.response.CreateSurveyResponse
import com.example.doohyeononboarding.api.service.response.UpdateSurveyResponse
import com.example.doohyeononboarding.domain.question.Question
import com.example.doohyeononboarding.domain.servey.Survey
import com.example.doohyeononboarding.domain.servey.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SurveyService(
    private val surveyRepository: SurveyRepository,
) {

    /**
     * 설문 등록
     */
    @Transactional
    fun createSurvey(request: CreateSurveyRequest): CreateSurveyResponse {
        require(request.questions.size in 1..10) {
            "설문 항목은 1개 이상 10개 이하이어야 합니다."
        }

        val survey = Survey(
            title = request.title,
            description = request.description,
        )

        val questions = request.questions.map { question ->
            Question(
                title = question.title,
                description = question.description,
                type = question.type,
                isRequired = question.isRequired,
                survey = survey,
                options = question.options ?: mutableListOf()
            )
        }
        survey.questions = questions

        val savedSurvey = surveyRepository.save(survey)

        return CreateSurveyResponse(savedSurvey.surveyId)
    }

    /**
     * 설문 수정
     */
    @Transactional
    fun updateSurvey(surveyId: Long, request: UpdateSurveyRequest): UpdateSurveyResponse {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("설문조사 아이디($surveyId)를 찾을 수 없습니다.") }

        survey.updateTitle(request.title)
        survey.updateDescription(request.description ?: "")
        survey.questions?.forEach { question ->
            val updatedQuestion = request.questions?.find { it.questionId == question.questionId }

            if (updatedQuestion != null) {
                question.updateTitle(updatedQuestion.title)
                question.updateDescription(updatedQuestion.description ?: "")
                question.updateType(updatedQuestion.type)
                question.updateIsRequired(updatedQuestion.isRequired)
                question.updateOptions(updatedQuestion.options ?: mutableListOf())
            }
        }

        val updatedSurvey = surveyRepository.save(survey)
        return UpdateSurveyResponse(updatedSurvey.surveyId)
    }

}

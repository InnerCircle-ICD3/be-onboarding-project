package com.example.doohyeononboarding.api.service

import com.example.doohyeononboarding.api.service.request.CreateSurveyRequest
import com.example.doohyeononboarding.api.service.response.CreateSurveyResponse
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

        return CreateSurveyResponse(
            surveyId = savedSurvey.surveyId,
        )
    }

}

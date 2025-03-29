package com.example.service

import com.example.dto.SurveyItemResponse
import com.example.dto.SurveyResponse
import com.example.repository.SurveyRepository
import com.example.repository.SurveyAnswerRepository
import org.springframework.stereotype.Service

@Service
class GetSurveyService(
    private val surveyRepository: SurveyRepository,
    private val surveyAnswerRepository: SurveyAnswerRepository
) {
    fun getSurvey(
        surveyId: Long,
        filterName: String? = null,
        filterAnswer: String? = null
    ): SurveyResponse {
        // 더미
        return SurveyResponse(
            id = surveyId,
            title = "test",
            description = "desc",
            items = emptyList<SurveyItemResponse>()
        )
    }
}

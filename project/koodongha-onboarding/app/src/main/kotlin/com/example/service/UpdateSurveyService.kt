package com.example.service

import com.example.dto.AnswerSubmitDto
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class UpdateSurveyService(
    private val surveyRepository: SurveyRepository,
    private val surveyAnswerRepository: SurveyAnswerRepository
) {
    fun submitAnswer(surveyId: Long, request: AnswerSubmitDto) {
        // TODO: 구현 예정
    }
}

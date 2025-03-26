package com.example.service

import com.example.dto.SurveyItemResponse
import com.example.dto.SurveyResponse
import com.example.entity.SurveyAnswer
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class GetSurveyService(
    private val surveyRepository: SurveyRepository,
    private val answerRepository: SurveyAnswerRepository
) {

    fun getSurvey(
        surveyId: Long,
        filterName: String? = null,
        filterAnswer: String? = null
    ): SurveyResponse {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { RuntimeException("Survey not found.") }

        val answers: List<SurveyAnswer> = answerRepository.findBySurveyId(surveyId)

        val itemResponses = survey.items.map { item ->
            val itemAnswers = answers
                .filter { it.surveyItem.id == item.id }
                .mapNotNull { it.shortAnswer }

            val filteredAnswers = if (
                filterName != null && filterAnswer != null &&
                item.name == filterName
            ) {
                itemAnswers.filter { ans ->
                    ans.split(",").map { it.trim() }.contains(filterAnswer)
                }
            } else {
                itemAnswers
            }

            SurveyItemResponse(
                id = item.id!!,
                name = item.name,
                description = item.description,
                inputType = item.inputType,
                isRequired = item.isRequired,
                answers = filteredAnswers,
                options = item.options.map { it.value }
            )
        }

        val filteredItems = if (filterName != null && filterAnswer != null) {
            itemResponses.filter { it.answers.orEmpty().isNotEmpty() }
        } else {
            itemResponses
        }

        return SurveyResponse(
            id = survey.id!!,
            title = survey.title,
            description = survey.description,
            items = filteredItems
        )
    }
}

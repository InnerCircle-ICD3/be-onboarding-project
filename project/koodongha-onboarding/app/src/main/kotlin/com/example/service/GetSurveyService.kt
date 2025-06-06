package com.example.service

import com.example.dto.*
import com.example.entity.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.exception.SurveyNotFoundException
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
            .orElseThrow { SurveyNotFoundException() }

        val allAnswers = answerRepository.findBySurveyId(surveyId)

        val itemResponses = survey.items.mapNotNull { item ->
            val itemAnswers = allAnswers.filter { it.item.id == item.id }
                .flatMap { it.getAnswerValues() }

            val filteredAnswers = if (
                filterName != null && filterAnswer != null && item.name == filterName
            ) {
                itemAnswers.filter { it.trim() == filterAnswer }
            } else {
                itemAnswers
            }

            when (item) {
                is TextItem -> TextItemResponse(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    isRequired = item.isRequired,
                    isLong = item.isLong,
                    answers = filteredAnswers
                )

                is ChoiceItem -> ChoiceItemResponse(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    isRequired = item.isRequired,
                    isMultiple = item.isMultiple,
                    options = item.options.map { it.value },
                    answers = filteredAnswers
                )

                else -> null
            }
        }

        val filteredItems = if (filterName != null && filterAnswer != null) {
            itemResponses.filterNot { it.answers.isNullOrEmpty() }
        } else {
            itemResponses
        }

        return SurveyResponse(
            id = survey.id,
            title = survey.title,
            description = survey.description,
            items = filteredItems
        )
    }
}

package com.example.service

import com.example.dto.*
import com.example.entity.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.exception.SurveyNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetSurveyService(
    private val surveyRepository: SurveyRepository,
    private val answerRepository: SurveyAnswerRepository
) {

    @Transactional(readOnly = true)
    fun getSurvey(
        surveyId: Long,
        filterName: String? = null,
        filterAnswer: String? = null
    ): SurveyResponse {
        val survey = surveyRepository.findSurveyWithItemsAndAnswers(surveyId)
            ?: throw SurveyNotFoundException()

        val allAnswers = answerRepository.findBySurveyId(surveyId)

        val itemResponses = survey.items.mapNotNull { item ->
            val itemAnswers = allAnswers.filter { it.item.id == item.id }

            val filteredValues = itemAnswers
                .flatMap { it.getAnswerValues() }
                .filter {
                    if (filterName != null && filterAnswer != null && item.name == filterName) {
                        it.trim() == filterAnswer
                    } else true
                }

            if (filterName != null && filterAnswer != null && item.name == filterName && filteredValues.isEmpty()) {
                return@mapNotNull null
            }

            when (item) {
                is TextItem -> TextItemResponse(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    isRequired = item.isRequired,
                    isLong = item.isLong,
                    answers = filteredValues
                )
                is ChoiceItem -> ChoiceItemResponse(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    isRequired = item.isRequired,
                    isMultiple = item.isMultiple,
                    options = item.options.map { it.value },
                    answers = filteredValues
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
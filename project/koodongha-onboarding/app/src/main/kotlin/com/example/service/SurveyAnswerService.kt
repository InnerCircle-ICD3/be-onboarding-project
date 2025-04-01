package com.example.service

import com.example.dto.*
import com.example.entity.*
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import com.example.exception.InvalidSurveyRequestException
import com.example.exception.SurveyNotFoundException
import org.springframework.stereotype.Service

@Service
class SurveyAnswerService(
    private val surveyRepository: SurveyRepository,
    private val surveyAnswerRepository: SurveyAnswerRepository
) {

    fun submitAnswer(surveyId: Long, request: AnswerSubmitDto) {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { SurveyNotFoundException() }

        val itemMap = survey.items.associateBy { it.id }
        val answeredItemIds = request.answers.map { it.itemId }.toSet()

        // 1. 필수 항목 응답 누락 검사
        val missingRequiredItems = survey.items.filter { it.isRequired && it.id !in answeredItemIds }
        if (missingRequiredItems.isNotEmpty()) {
            throw InvalidSurveyRequestException("Required questions must be answered.")
        }

        val answers = request.answers.map { dto ->
            val item = itemMap[dto.itemId]
                ?: throw InvalidSurveyRequestException("Answer value does not match survey item.")

            when (dto) {
                is TextAnswerDto -> {
                    if (item !is TextItem) {
                        throw InvalidSurveyRequestException("Item is not of type text.")
                    }

                    // 2. 단답형만 길이 제한 적용
                    if (!item.isLong && dto.value.length > 255) {
                        throw InvalidSurveyRequestException("SHORT_TEXT answers must be within 255 characters.")
                    }

                    TextAnswer(
                        content = dto.value,
                        survey = survey,
                        item = item
                    )
                }

                is ChoiceAnswerDto -> {
                    if (item !is ChoiceItem) {
                        throw InvalidSurveyRequestException("Item is not of type choice.")
                    }

                    val selected = dto.selectedOptionIds.mapNotNull { id ->
                        item.options.find { it.id == id }
                    }

                    if (selected.size != dto.selectedOptionIds.size) {
                        throw InvalidSurveyRequestException("You must enter a valid answer for the selected options.")
                    }

                    ChoiceAnswer(
                        selectedOptions = selected.toMutableList(),
                        survey = survey,
                        item = item
                    )
                }
            }
        }

        surveyAnswerRepository.saveAll(answers)
    }
}

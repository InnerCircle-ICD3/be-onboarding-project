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

        // 필수 항목 누락 여부 검증 (중복 응답 허용 기준)
        val missingRequiredItems = survey.items.filter {
            it.isRequired && it.id !in answeredItemIds
        }
        if (missingRequiredItems.isNotEmpty()) {
            throw InvalidSurveyRequestException("Required questions must be answered.")
        }

        val answers = request.answers.map { dto ->
            val item = itemMap[dto.itemId]
                ?: throw InvalidSurveyRequestException("Answer value does not match survey item.")

            when (dto) {
                is TextAnswerDto -> createTextAnswer(dto, item, survey)
                is ChoiceAnswerDto -> createChoiceAnswer(dto, item, survey)
            }
        }

        surveyAnswerRepository.saveAll(answers)
    }

    private fun createTextAnswer(dto: TextAnswerDto, item: SurveyItemBase, survey: Survey): TextAnswer {
        if (item !is TextItem) {
            throw InvalidSurveyRequestException("Item is not of type text.")
        }
        if (!item.isLong && dto.value.length > 255) {
            throw InvalidSurveyRequestException("SHORT_TEXT answers must be within 255 characters.")
        }
        if (dto.value.trim().isEmpty() && item.isRequired) {
            throw InvalidSurveyRequestException("Required questions must be answered.")
        }

        return TextAnswer(
            content = dto.value,
            questionName = item.name,
            questionType = "TEXT",
            survey = survey,
            item = item
        )
    }

    private fun createChoiceAnswer(dto: ChoiceAnswerDto, item: SurveyItemBase, survey: Survey): ChoiceAnswer {
        if (item !is ChoiceItem) {
            throw InvalidSurveyRequestException("Item is not of type choice.")
        }

        val selected = dto.selectedOptionIds.mapNotNull { id ->
            item.options.find { it.id == id }
        }

        if (selected.size != dto.selectedOptionIds.size) {
            throw InvalidSurveyRequestException("One or more selected option IDs are invalid.")
        }

        if (selected.isEmpty() && item.isRequired) {
            throw InvalidSurveyRequestException("Required questions must be answered.")
        }

        return ChoiceAnswer(
            selectedValues = selected.map { it.value },
            questionName = item.name,
            questionType = "CHOICE",
            survey = survey,
            item = item
        )
    }
}

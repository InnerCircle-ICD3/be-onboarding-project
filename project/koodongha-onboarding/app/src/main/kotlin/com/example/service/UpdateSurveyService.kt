package com.example.service

import com.example.dto.AnswerSubmitDto
import com.example.entity.InputType
import com.example.entity.SurveyAnswer
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import org.springframework.stereotype.Service

@Service
class UpdateSurveyService(
    private val surveyRepository: SurveyRepository,
    private val surveyAnswerRepository: SurveyAnswerRepository
) {

    fun submitAnswer(surveyId: Long, request: AnswerSubmitDto) {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("Survey not found.") }

        val itemMap = survey.items.associateBy { it.id }

        val answers = request.answers.map { dto ->
            val item = itemMap[dto.itemId]
                ?: throw IllegalArgumentException("Answer value does not match survey item.")

            if (item.inputType.isChoice()) {
                val validOptions = item.options.map { it.value }
                dto.values.forEach { value ->
                    if (value !in validOptions) {
                        throw IllegalArgumentException("You must enter a valid answer for the selected options.")
                    }
                }
            } else if (item.inputType == InputType.SHORT_TEXT) {
                dto.values.forEach { value ->
                    if (value.length > 255) {
                        throw IllegalArgumentException("SHORT_TEXT answers must be within 255 characters.")
                    }
                }
            }

            val selectedOptions = if (item.inputType.isChoice()) {
                dto.values.mapNotNull { value ->
                    item.options.find { it.value == value }
                }.toMutableList()
            } else {
                mutableListOf()
            }

            SurveyAnswer(
                survey = survey,
                surveyItem = item,
                shortAnswer = if (item.inputType.isChoice()) null else dto.values.joinToString(","),
                selectedOptions = selectedOptions
            )
        }

        surveyAnswerRepository.saveAll(answers)
    }

    private fun InputType.isChoice(): Boolean =
        this == InputType.SINGLE_CHOICE || this == InputType.MULTI_CHOICE
}

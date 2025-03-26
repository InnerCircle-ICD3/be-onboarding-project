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

            dto.values.forEach { value ->
                if (item.inputType.isChoice()) {
                    val validOptions = item.options.map { it.value }
                    if (!validOptions.contains(value)) {
                        throw IllegalArgumentException("You must enter a valid answer for the selected options.")
                    }
                }

                if (item.inputType == InputType.SHORT_TEXT && value.length > 255) {
                    throw IllegalArgumentException("SHORT_TEXT answers must be within 255 characters.")
                }
            }

            SurveyAnswer(
                survey = survey,
                surveyItem = item,
                shortAnswer = dto.values.joinToString(",")
            )
        }

        surveyAnswerRepository.saveAll(answers)
    }

    private fun InputType.isChoice(): Boolean {
        return this == InputType.SINGLE_CHOICE || this == InputType.MULTI_CHOICE
    }
}

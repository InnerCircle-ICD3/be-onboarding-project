package com.example.service

import com.example.dto.*
import com.example.entity.*
import com.example.exception.InvalidSurveyRequestException
import com.example.exception.SurveyNotFoundException
import com.example.repository.SurveyAnswerRepository
import com.example.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateSurveyService(
    private val surveyRepository: SurveyRepository,
    private val surveyAnswerRepository: SurveyAnswerRepository
) {

    @Transactional
    fun updateSurvey(surveyId: Long, request: SurveyUpdateRequest) {
        val survey = surveyRepository.findSurveyWithItemsAndAnswers(surveyId)
        ?: throw SurveyNotFoundException()

        // 1. 설문 제목 & 설명 수정
        survey.title = request.title
        survey.description = request.description

        // 2. 기존 항목 매핑
        val existingItems = survey.items.associateBy { it.id }

        // 3. 요청된 항목 순회하며 갱신 or 생성
        val updatedItems = request.items.map { itemRequest ->
            when (itemRequest) {
                is TextItemUpdateRequest -> {
                    if (itemRequest.id == null) {
                        TextItem(
                            name = itemRequest.name,
                            description = itemRequest.description,
                            isRequired = itemRequest.isRequired,
                            isLong = itemRequest.isLong,
                            survey = survey
                        )
                    } else {
                        val existing = existingItems[itemRequest.id]
                        if (existing !is TextItem)
                            throw InvalidSurveyRequestException("Invalid item type.")
                        existing.apply {
                            name = itemRequest.name
                            description = itemRequest.description
                            isRequired = itemRequest.isRequired
                            isLong = itemRequest.isLong
                        }
                    }
                }

                is ChoiceItemUpdateRequest -> {
                    if (itemRequest.id == null) {
                        val newChoiceItem = ChoiceItem(
                            name = itemRequest.name,
                            description = itemRequest.description,
                            isRequired = itemRequest.isRequired,
                            isMultiple = itemRequest.isMultiple,
                            survey = survey
                        )
                        itemRequest.options.forEach {
                            newChoiceItem.options.add(SelectionOption(value = it, item = newChoiceItem))
                        }
                        newChoiceItem
                    } else {
                        val existing = existingItems[itemRequest.id]
                        if (existing !is ChoiceItem)
                            throw InvalidSurveyRequestException("Invalid item type.")
                        existing.apply {
                            name = itemRequest.name
                            description = itemRequest.description
                            isRequired = itemRequest.isRequired
                            isMultiple = itemRequest.isMultiple
                            options.clear()
                            itemRequest.options.forEach {
                                options.add(SelectionOption(value = it, item = this))
                            }
                        }
                    }
                }
            }
        }

        // 4. 항목 전체 교체 (orphanRemoval = true 로 삭제 반영됨)
        survey.items.clear()
        survey.items.addAll(updatedItems)

        // 5. 저장 (JPA dirty checking)
        surveyRepository.save(survey)
    }
}

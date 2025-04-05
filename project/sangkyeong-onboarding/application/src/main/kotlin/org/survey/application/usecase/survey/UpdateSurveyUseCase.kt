package org.survey.application.usecase.survey

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.survey.application.dto.command.UpdateSurveyCommand
import org.survey.application.dto.command.UpdateSurveyItemCommand
import org.survey.domain.survey.model.SurveyItem
import org.survey.domain.survey.service.SurveyCommandService
import org.survey.domain.survey.service.SurveyQueryService

@Service
class UpdateSurveyUseCase(
    private val surveyCommandService: SurveyCommandService,
    private val surveyQueryService: SurveyQueryService,
) {
    @Transactional
    fun execute(
        surveyId: Long,
        request: UpdateSurveyCommand,
    ) {
        /**
         * 1. 설문조사 제목, 설명 업데이트
         * 2. 기존 항목과 요청 항목 비교
         * 3. 요청에 없는 기존 항목은 논리적 삭제
         * 4. 새 항목 추가
         * 5. 기존 항목 업데이트
         * 6. 선택형 항목의 사용하지 않는 옵션 논리적 삭제 TODO
         * 7. 선택형 항목 의 새 옵션 추가 TODO
         * 8. 선택형 항목 의 기존 옵션 업데이트 TODO
         */
        surveyCommandService.updateSurvey(
            surveyId = surveyId,
            title = request.title,
            description = request.description,
        )

        val existingItems = surveyQueryService.getSurveyItems(surveyId)
        surveyCommandService.deleteNotUsingSurveyItems(
            existingItems,
            request.items.mapNotNull { it.id }.toSet(),
        )

        val newItems = request.items.filter { it.id == null }
        if (newItems.isNotEmpty()) {
            surveyCommandService.addSurveyItems(
                surveyId,
                newItems.map { createNewSurveyItem(it, surveyId) },
            )
        }

        val existingItemsToUpdate = request.items.filter { it.id != null }
        if (existingItemsToUpdate.isNotEmpty()) {
            surveyCommandService.updateSurveyItems(
                existingItemsToUpdate.map { updateExistingSurveyItem(it, surveyId) },
            )
        }
    }

    private fun createNewSurveyItem(
        item: UpdateSurveyItemCommand,
        surveyId: Long,
    ): SurveyItem {
        return SurveyItem(
            surveyId = surveyId,
            title = item.title,
            description = item.description,
            inputType = item.inputType,
            isRequired = item.isRequired,
            options = processItemOptions(item),
        )
    }

    private fun updateExistingSurveyItem(
        item: UpdateSurveyItemCommand,
        surveyId: Long,
    ): SurveyItem {
        return SurveyItem(
            id = item.id!!,
            surveyId = surveyId,
            title = item.title,
            description = item.description,
            inputType = item.inputType,
            isRequired = item.isRequired,
            options = processItemOptions(item),
        )
    }

    private fun processItemOptions(item: UpdateSurveyItemCommand): List<String> {
        return if (item.inputType in listOf("SINGLE_CHOICE", "MULTIPLE_CHOICE")) {
            validateItemOptions(item)
            item.options!!.map { option -> option.value }
        } else {
            emptyList()
        }
    }

    private fun validateItemOptions(item: UpdateSurveyItemCommand) {
        val optionSize =
            item.options?.size
                ?: throw IllegalArgumentException("선택형 질문에는 최소 1개 이상의 선택지가 필요합니다.")

        if (optionSize > 10) {
            throw IllegalArgumentException("선택형 질문은 최대 10개까지 가능합니다.")
        }
    }
}

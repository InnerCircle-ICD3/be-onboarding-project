package org.survey.application.usecase.response

import org.springframework.stereotype.Service
import org.survey.application.dto.response.ChoiceAnswerResponse
import org.survey.application.dto.response.OptionResponse
import org.survey.application.dto.response.SurveyAnswerResponse
import org.survey.application.dto.response.TextAnswerResponse
import org.survey.domain.response.model.ChoiceAnswer
import org.survey.domain.response.model.TextAnswer
import org.survey.domain.response.service.ResponseDomainService
import org.survey.domain.survey.service.SurveyQueryService

@Service
class GetSurveyResponsesUseCase(
    private val responseDomainService: ResponseDomainService,
    private val surveyQueryService: SurveyQueryService,
) {
    /**
     * 1. 설문 응답 데이터 조회합니다.
     * 2. 설문조사 정보 조회합니다.
     * 3. 설문 항목 정보 조회 및 맵으로 변환합니다.
     * 4. 설문 옵션 정보 조회합니다.
     * 5. 응답 데이터 구성합니다.
     */
    fun execute(surveyId: Long): List<SurveyAnswerResponse> {
        val responses = responseDomainService.getSurveyResponses(surveyId)
        if (responses.isEmpty()) return emptyList()

        val survey = surveyQueryService.getSurvey(surveyId)

        val surveyItems =
            surveyQueryService.getSurveyItems(surveyId)
                .associateBy { it.id }

        val itemIds = surveyItems.keys.toList()
        val itemOptions =
            surveyQueryService.getItemOptions(itemIds)
                .groupBy { it.surveyItemId }

        return responses.map { response ->
            SurveyAnswerResponse(
                surveyTitle = survey.title,
                surveyDescription = survey.description,
                createdAt = response.createdAt,
                answers =
                    response.answers.map { answer ->
                        val item = surveyItems[answer.surveyItemId]

                        when (answer) {
                            is TextAnswer ->
                                TextAnswerResponse(
                                    surveyItemTitle = item?.title ?: "",
                                    surveyItemDescription = item?.description,
                                    value = answer.value,
                                )

                            is ChoiceAnswer -> {
                                val options =
                                    itemOptions[answer.surveyItemId]
                                        ?.filter { it.id in answer.itemOptionIds }
                                        ?.map { OptionResponse(value = it.value) }
                                        ?: emptyList()

                                ChoiceAnswerResponse(
                                    surveyItemTitle = item?.title ?: "",
                                    surveyItemDescription = item?.description,
                                    options = options,
                                )
                            }
                        }
                    },
            )
        }
    }
}

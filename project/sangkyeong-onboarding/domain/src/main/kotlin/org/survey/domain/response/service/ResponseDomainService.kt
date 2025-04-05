package org.survey.domain.response.service

import org.springframework.stereotype.Service
import org.survey.domain.response.model.Answer
import org.survey.domain.response.model.ChoiceAnswer
import org.survey.domain.response.model.SurveyResponse
import org.survey.domain.response.model.SurveyResponseDetail
import org.survey.domain.response.model.TextAnswer
import org.survey.domain.response.repository.ChoiceAnswerRepository
import org.survey.domain.response.repository.SurveyResponseRepository
import org.survey.domain.response.repository.TextAnswerRepository

@Service
class ResponseDomainService(
    private val surveyResponseRepository: SurveyResponseRepository,
    private val textAnswerRepository: TextAnswerRepository,
    private val choiceAnswerRepository: ChoiceAnswerRepository,
) {
    fun submitSurveyResponse(
        surveyId: Long,
        request: List<Answer>,
    ) {
        /**
         * 1. 응답을 저장하기 위해 SurveyResponse 객체를 생성합니다.
         * 2. SurveyResponse 객체를 저장하여 ID를 생성합니다.
         * 3. 요청된 답변을 텍스트 응답과 선택 응답으로 분류합니다.
         * 4. 텍스트 응답을 TextAnswer 객체로 변환하여 저장합니다.
         * 5. 선택 응답을 ChoiceAnswer 객체로 변환하여 저장합니다.
         * 6. 모든 답변을 SurveyResponse 객체에 연결합니다.
         * 7. 응답을 저장합니다.
         */
        val surveyResponse =
            SurveyResponse(
                surveyId = surveyId,
            )
        val surveyResponseId = surveyResponseRepository.save(surveyResponse)

        processBatchAnswers(surveyResponseId, request)
    }

    private fun processBatchAnswers(
        surveyResponseId: Long,
        answers: List<Answer>,
    ) {
        val (textRequests, choiceRequests) = answers.partition { it is TextAnswer }

        val textAnswers =
            textRequests
                .map { it as TextAnswer }
                .map {
                    TextAnswer(
                        surveyResponseId = surveyResponseId,
                        surveyItemId = it.surveyItemId,
                        value = it.value,
                    )
                }

        if (textAnswers.isNotEmpty()) {
            textAnswerRepository.saveAll(textAnswers)
        }

        val choiceAnswers =
            choiceRequests
                .map { it as ChoiceAnswer }
                .map {
                    ChoiceAnswer(
                        surveyResponseId = surveyResponseId,
                        surveyItemId = it.surveyItemId,
                        itemOptionIds = it.itemOptionIds,
                    )
                }

        if (choiceAnswers.isNotEmpty()) {
            choiceAnswerRepository.saveAll(choiceAnswers)
        }
    }

    fun getSurveyResponses(surveyId: Long): List<SurveyResponseDetail> {
        val responses = surveyResponseRepository.findAllBySurveyId(surveyId)

        if (responses.isEmpty()) {
            return emptyList()
        }

        val responseIds = responses.map { it.id }

        val textAnswersByResponseId =
            textAnswerRepository.findAllBySurveyResponseIds(responseIds)
                .groupBy { it.surveyResponseId }

        val choiceAnswersByResponseId =
            choiceAnswerRepository.findAllBySurveyResponseIds(responseIds)
                .groupBy { it.surveyResponseId }

        return responses.map { response ->
            val textAnswers = textAnswersByResponseId[response.id] ?: emptyList()
            val choiceAnswers = choiceAnswersByResponseId[response.id] ?: emptyList()

            val sortedAnswers =
                (textAnswers + choiceAnswers)
                    .sortedBy { it.surveyItemId }

            SurveyResponseDetail(
                id = response.id,
                surveyId = response.surveyId,
                answers = sortedAnswers,
                createdAt = response.createdAt,
            )
        }
    }
}

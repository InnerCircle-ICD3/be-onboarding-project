package com.chosseang.seonghunonboarding.service

import com.chosseang.seonghunonboarding.common.JsonConverter
import com.chosseang.seonghunonboarding.dto.AnswerResponse
import com.chosseang.seonghunonboarding.dto.AnswerSubmitRequest
import com.chosseang.seonghunonboarding.entity.Answer
import com.chosseang.seonghunonboarding.repository.AnswerRepository
import com.chosseang.seonghunonboarding.repository.SurveyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AnswerService (
    private val answerRepository: AnswerRepository,
    private val surveyRepository: SurveyRepository,
    private val jsonConverter: JsonConverter
){
    fun submitAnswer(request: AnswerSubmitRequest): AnswerResponse {
        // 설문조사 찾기
        val survey = surveyRepository.findByIdOrNull(request.surveyId)
            ?: throw IllegalArgumentException("Survey not found with id: ${request.surveyId}")

        val itemsJson = jsonConverter.convertItemsToJson(request.items)
        val responsesJson = jsonConverter.convertResponsesToJson(request.responses)

        // Answer 엔티티 생성
        val answer = Answer(
            id = null,  // 새로운 응답이므로 id는 null로 설정
            name = request.name,
            items = itemsJson,
            responses = responsesJson,
            survey = survey
        )

        // 저장 및 응답 변환
        val savedAnswer = answerRepository.save(answer)

        return AnswerResponse(
            id = savedAnswer.id,
            name = savedAnswer.name,
            items = request.items,  // 원본 데이터 사용
            responses = request.responses
        )
    }
}

package com.hjpark.survey

import com.hjpark.shared.exception.CustomException
import com.hjpark.shared.surveyDto.*
import com.hjpark.survey.entity.*
import com.hjpark.survey.repository.QuestionRepository
import com.hjpark.survey.repository.SurveyResponseRepository
import com.hjpark.survey.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResponseService(
    private val surveyResponseRepository: SurveyResponseRepository,
    private val questionRepository: QuestionRepository,
    private val surveyRepository: SurveyRepository
) {

    /** 단일 설문에 대한 모든 응답을 조회 */
    @Transactional(readOnly = true)
    fun getResponses(surveyId: Long): List<ResponseRetrievalResponse> {
        return surveyResponseRepository.findBySurveyId(surveyId).map { response ->
            ResponseRetrievalResponse(
                responseId = response.id!!,
                respondentId = response.respondentId,
                status = response.status!!,
                createTime = response.createTime!!,
                answers = response.items.map { item ->
                    AnswerResponse(
                        questionId = item.questionId,
                        textValue = item.textValue,
                        selectedOptions = item.option?.let {
                            listOf(OptionResponse(it.id!!, it.text, it.sequence))
                        }
                    )
                }
            )
        }
    }

    /** 설문에 대한 응답을 제출 */
    @Transactional
    fun submitResponse(request: SubmitResponseRequest): ResponseSubmissionResponse {
        // 1. 설문 존재 여부 확인
        val survey = surveyRepository.findById(request.surveyId)
            .orElseThrow {
                CustomException(
                    userMessage = "존재하지 않는 설문입니다",
                    detailMessage = "Survey ID: ${request.surveyId} not found"
                )
            }

        // 2. 응답 객체 생성
        val response = SurveyResponse(
            survey = survey,
            respondentId = request.respondentId,
            status = ResponseStatus.COMPLETED.name
        )

        // 3. 응답 항목 처리
        request.answers.forEach { answer ->
            val question = questionRepository.findById(answer.questionId)
                .orElseThrow {
                    CustomException(
                        userMessage = "존재하지 않는 질문입니다",
                        detailMessage = "Question ID: ${answer.questionId} not found"
                    )
                }

            validateAnswer(question, answer)

            val responseItem = ResponseItem(
                surveyResponse = response,
                questionId = question.id!!,
                textValue = answer.textValue,
                option = if (answer.optionIds.isNullOrEmpty()) null
                else getOption(answer.optionIds!!.first(), question)
            )
            response.items.add(responseItem)
        }

        // 4. 저장 및 결과 반환
        val savedResponse = surveyResponseRepository.save(response)
        return ResponseSubmissionResponse(
            responseId = savedResponse.id!!,
            status = savedResponse.status!!,
            createTime = savedResponse.createTime!!
        )
    }

    // ============================== 유틸리티 메서드 ==============================

    /** 질문에 대한 응답의 유효성을 검증 */
    private fun validateAnswer(question: Question, answer: AnswerRequest) {
        // 필수 질문 검증
        if (question.required && answer.textValue.isNullOrEmpty() && answer.optionIds.isNullOrEmpty()) {
            throw CustomException(
                userMessage = "필수 질문에 답변해야 합니다",
                detailMessage = "Question: ${question.name} requires an answer"
            )
        }

        // 2. 질문 유형별 검증
        when (question.type) {
            // 텍스트 기반 질문 (단답형/장문형)
            QuestionType.SHORT_ANSWER, QuestionType.LONG_ANSWER -> {
                if (!answer.optionIds.isNullOrEmpty()) {
                    throw CustomException(
                        userMessage = "텍스트 형식 질문에는 옵션을 선택할 수 없습니다",
                        detailMessage = "Text-based question cannot have options: ${question.id}"
                    )
                }
            }

            // 선택 기반 질문 (단일/다중 선택)
            QuestionType.SINGLE_CHOICE, QuestionType.MULTIPLE_CHOICE -> {
                // 2-1. 텍스트 값 입력 금지
                if (!answer.textValue.isNullOrEmpty()) {
                    throw CustomException(
                        userMessage = "선택 질문에는 텍스트를 입력할 수 없습니다",
                        detailMessage = "Choice-based question cannot have text: ${question.id}"
                    )
                }

                // 2-2. 옵션 개수 검증
                when (question.type) {
                    QuestionType.SINGLE_CHOICE -> {
                        if (answer.optionIds?.size != 1) {
                            throw CustomException(
                                userMessage = "단일 선택 질문에는 정확히 1개의 옵션을 선택해야 합니다",
                                detailMessage = "Single-choice requires exactly 1 option: ${question.id}"
                            )
                        }
                    }
                    QuestionType.MULTIPLE_CHOICE -> {
                        if (answer.optionIds.isNullOrEmpty()) {
                            throw CustomException(
                                userMessage = "다중 선택 질문에는 최소 1개 이상의 옵션을 선택해야 합니다",
                                detailMessage = "Multiple-choice requires at least 1 option: ${question.id}"
                            )
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    /** 질문에 대한 옵션을 조회 */
    private fun getOption(optionId: Long, question: Question): QuestionOption {
        return question.options.find { it.id == optionId }
            ?: throw CustomException(
                userMessage = "유효하지 않은 옵션 선택입니다",
                detailMessage = "Option ID: $optionId not found for Question: ${question.name}"
            )
    }
}

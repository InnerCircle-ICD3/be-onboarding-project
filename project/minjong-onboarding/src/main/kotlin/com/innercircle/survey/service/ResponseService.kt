// src/main/kotlin/com/innercircle/survey/service/ResponseService.kt
package com.innercircle.survey.service

import com.innercircle.survey.domain.Response
import com.innercircle.survey.domain.ResponseItem
import com.innercircle.survey.dto.*
import com.innercircle.survey.exception.ResourceNotFoundException
import com.innercircle.survey.exception.ValidationException
import com.innercircle.survey.repository.QuestionRepository
import com.innercircle.survey.repository.ResponseItemRepository
import com.innercircle.survey.repository.ResponseRepository
import com.innercircle.survey.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResponseService(
    private val surveyRepository: SurveyRepository,
    private val questionRepository: QuestionRepository,
    private val responseRepository: ResponseRepository,
    private val responseItemRepository: ResponseItemRepository
) {

    // 설문 응답 제출
    @Transactional
    fun submitResponse(surveyId: Long, request: SubmitResponseRequest): ResponseResponse {
        // 설문조사 존재 확인
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { ResourceNotFoundException("Survey not found with id: $surveyId") }
        
        // 활성화된 질문 목록 조회
        val activeQuestions = questionRepository.findBySurveyAndDeletedFalseOrderByPositionAsc(survey)
        
        // 필수 응답 항목 확인
        val requiredQuestionIds = activeQuestions
            .filter { it.required }
            .map { it.id!! }
            .toSet()
        
        val submittedQuestionIds = request.items.map { it.questionId }.toSet()
        
        // 필수 항목 누락 검증
        val missingRequiredQuestions = requiredQuestionIds - submittedQuestionIds
        if (missingRequiredQuestions.isNotEmpty()) {
            throw ValidationException("Missing required questions: $missingRequiredQuestions")
        }
        
        // 유효하지 않은 질문 ID 검증
        val validQuestionIds = activeQuestions.map { it.id!! }.toSet()
        val invalidQuestionIds = submittedQuestionIds - validQuestionIds
        if (invalidQuestionIds.isNotEmpty()) {
            throw ValidationException("Invalid question IDs: $invalidQuestionIds")
        }
        
        // 응답 엔티티 생성
        val response = Response(survey = survey)
        
        // 응답 항목 추가
        request.items.forEach { itemRequest ->
            // 질문 조회
            val question = activeQuestions.find { it.id == itemRequest.questionId }
                ?: throw ResourceNotFoundException("Question not found with id: ${itemRequest.questionId}")
            
            // 단일/다중 선택형인 경우, 유효한 옵션 값인지 검증
            if (question.questionType.name == "SINGLE_SELECT" || question.questionType.name == "MULTI_SELECT") {
                // 다중 선택형은 JSON 배열 형태로 값 전달 (간단한 검증)
                // 실제로는 더 복잡한 검증 로직 필요
            }
            
            // 응답 항목 생성 및 추가
            val responseItem = ResponseItem(
                question = question,
                answerValue = itemRequest.answerValue
            )
            response.addItem(responseItem)
        }
        
        // 저장 및 응답 반환
        val savedResponse = responseRepository.save(response)
        return mapToResponseResponse(savedResponse)
    }
    
    // 설문 응답 목록 조회
    @Transactional(readOnly = true)
    fun getSurveyResponses(surveyId: Long): List<ResponseResponse> {
        // 설문조사 존재 확인
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { ResourceNotFoundException("Survey not found with id: $surveyId") }
        
        // 응답 목록 조회
        val responses = responseRepository.findBySurvey(survey)
        
        // DTO 변환 및 반환
        return responses.map { mapToResponseResponse(it) }
    }
    
    // 응답 검색 (고급 기능)
    @Transactional(readOnly = true)
    fun searchResponses(surveyId: Long, request: SearchResponseRequest): List<ResponseResponse> {
        // 설문조사 존재 확인
        surveyRepository.findById(surveyId)
            .orElseThrow { ResourceNotFoundException("Survey not found with id: $surveyId") }
        
        // 키워드 검색
        val keyword = request.keyword
        
        if (keyword != null && keyword.isNotBlank()) {
            // 키워드로 응답 항목 검색
            val responseItems = responseItemRepository.searchByKeyword(surveyId, keyword)
            
            // 응답 항목으로 응답 ID 목록 추출
            val responseIds = responseItems.map { item -> item.response?.id!! }.distinct()
            
            // 응답 목록 조회 및 DTO 변환
            return responseIds.mapNotNull { responseId ->
                responseRepository.findById(responseId).orElse(null)
            }.map { response -> mapToResponseResponse(response) }
        }
        
        // 키워드가 없으면 전체 응답 조회
        return getSurveyResponses(surveyId)
    }
    
    // Response 엔티티를 ResponseResponse DTO로 변환
    private fun mapToResponseResponse(response: Response): ResponseResponse {
        return ResponseResponse(
            id = response.id!!,
            surveyId = response.survey.id!!,
            items = response.items.map { item ->
                ResponseItemResponse(
                    id = item.id!!,
                    questionId = item.question.id!!,
                    questionName = item.question.questionName,
                    questionType = item.question.questionType,
                    answerValue = item.answerValue
                )
            },
            createdAt = response.createdAt
        )
    }
}
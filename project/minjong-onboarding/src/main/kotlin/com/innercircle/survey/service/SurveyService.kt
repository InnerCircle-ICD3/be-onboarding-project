// src/main/kotlin/com/innercircle/survey/service/SurveyService.kt
package com.innercircle.survey.service

import com.innercircle.survey.domain.*
import com.innercircle.survey.dto.*
import com.innercircle.survey.exception.ResourceNotFoundException
import com.innercircle.survey.repository.OptionRepository
import com.innercircle.survey.repository.QuestionRepository
import com.innercircle.survey.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SurveyService(
    private val surveyRepository: SurveyRepository,
    private val questionRepository: QuestionRepository,
    private val optionRepository: OptionRepository
) {

    // 설문조사 생성
    @Transactional
    fun createSurvey(request: CreateSurveyRequest): SurveyResponse {
        // 설문조사 엔티티 생성
        val survey = Survey(
            title = request.title,
            description = request.description
        )
        
        // 질문 엔티티 생성 및 설문조사에 추가
        request.questions.forEachIndexed { index, questionRequest ->
            val question = Question(
                questionName = questionRequest.questionName,
                questionDescription = questionRequest.questionDescription,
                questionType = QuestionType.valueOf(questionRequest.questionType),
                required = questionRequest.required,
                position = questionRequest.position.takeIf { it > 0 } ?: index
            )
            
            survey.addQuestion(question)
            
            // 단일/다중 선택형 질문이면 선택지 추가
            if (question.questionType == QuestionType.SINGLE_SELECT || 
                question.questionType == QuestionType.MULTI_SELECT) {
                
                questionRequest.options?.forEachIndexed { optIndex, optionRequest ->
                    val option = Option(
                        value = optionRequest.value,
                        position = optionRequest.position.takeIf { it > 0 } ?: optIndex
                    )
                    question.addOption(option)
                }
            }
        }
        
        // 저장 및 응답 반환
        val savedSurvey = surveyRepository.save(survey)
        return mapToSurveyResponse(savedSurvey)
    }
    
    // 설문조사 조회
    @Transactional(readOnly = true)
    fun getSurvey(surveyId: Long): SurveyResponse {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { ResourceNotFoundException("Survey not found with id: $surveyId") }
        
        return mapToSurveyResponse(survey)
    }
    
    // 설문조사 수정
    @Transactional
    fun updateSurvey(surveyId: Long, request: UpdateSurveyRequest): SurveyResponse {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { ResourceNotFoundException("Survey not found with id: $surveyId") }
        
        // 설문조사 기본 정보 업데이트
        survey.title = request.title
        survey.description = request.description
        survey.updatedAt = LocalDateTime.now()
        
        // 기존 질문들 처리 (삭제 표시 또는 업데이트)
        val existingQuestions = questionRepository.findBySurveyOrderByPositionAsc(survey)
        val updatedQuestionIds = request.questions
            .filter { it.id != null }
            .map { it.id!! }
            .toSet()
        
        // 삭제된 질문은 deleted 플래그 설정 (실제 삭제 안함)
        existingQuestions.forEach { question ->
            if (question.id !in updatedQuestionIds) {
                question.deleted = true
            }
        }
        
        // 질문 업데이트 또는 추가
        request.questions.forEachIndexed { index, questionRequest ->
            if (questionRequest.id == null) {
                // 신규 질문 추가
                val newQuestion = Question(
                    questionName = questionRequest.questionName,
                    questionDescription = questionRequest.questionDescription,
                    questionType = QuestionType.valueOf(questionRequest.questionType),
                    required = questionRequest.required,
                    position = questionRequest.position.takeIf { it > 0 } ?: index
                )
                
                survey.addQuestion(newQuestion)
                
                // 단일/다중 선택형 질문이면 선택지 추가
                if (newQuestion.questionType == QuestionType.SINGLE_SELECT || 
                    newQuestion.questionType == QuestionType.MULTI_SELECT) {
                    
                    questionRequest.options?.forEachIndexed { optIndex, optionRequest ->
                        val option = Option(
                            value = optionRequest.value,
                            position = optionRequest.position.takeIf { it > 0 } ?: optIndex
                        )
                        newQuestion.addOption(option)
                    }
                }
            } else {
                // 기존 질문 업데이트
                val existingQuestion = existingQuestions.find { it.id == questionRequest.id }
                existingQuestion?.let { question ->
                    question.questionName = questionRequest.questionName
                    question.questionDescription = questionRequest.questionDescription
                    question.questionType = QuestionType.valueOf(questionRequest.questionType)
                    question.required = questionRequest.required
                    question.position = questionRequest.position.takeIf { it > 0 } ?: index
                    question.deleted = questionRequest.deleted
                    
                    // 선택지 관리 (단일/다중 선택형)
                    if (question.questionType == QuestionType.SINGLE_SELECT || 
                        question.questionType == QuestionType.MULTI_SELECT) {
                        
                        // 기존 선택지 전부 제거하고 새로 추가
                        // (실제 프로젝트에서는 기존 선택지 업데이트 로직 고려)
                        question.options.clear()
                        
                        questionRequest.options?.forEachIndexed { optIndex, optionRequest ->
                            val option = Option(
                                value = optionRequest.value,
                                position = optionRequest.position.takeIf { it > 0 } ?: optIndex
                            )
                            question.addOption(option)
                        }
                    } else {
                        // 단답형/장문형으로 변경된 경우 기존 선택지 제거
                        question.options.clear()
                    }
                }
            }
        }
        
        // 저장 및 응답 반환
        return mapToSurveyResponse(surveyRepository.save(survey))
    }
    
    // Survey 엔티티를 SurveyResponse DTO로 변환
    private fun mapToSurveyResponse(survey: Survey): SurveyResponse {
        val activeQuestions = survey.questions
            .filter { !it.deleted }
            .sortedBy { it.position }
        
        return SurveyResponse(
            id = survey.id!!,
            title = survey.title,
            description = survey.description,
            questions = activeQuestions.map { question ->
                QuestionResponse(
                    id = question.id!!,
                    questionName = question.questionName,
                    questionDescription = question.questionDescription,
                    questionType = question.questionType,
                    required = question.required,
                    options = if (question.options.isEmpty()) null 
                              else question.options.sortedBy { it.position }
                                  .map { option ->
                                      OptionResponse(
                                          id = option.id!!,
                                          value = option.value,
                                          position = option.position
                                      )
                                  },
                    position = question.position
                )
            },
            createdAt = survey.createdAt,
            updatedAt = survey.updatedAt
        )
    }
}
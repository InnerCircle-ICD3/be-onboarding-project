package com.hjpark.survey

import com.hjpark.shared.surveyDto.*
import com.hjpark.survey.entity.Question
import com.hjpark.survey.entity.QuestionOption
import com.hjpark.survey.entity.QuestionType
import com.hjpark.survey.entity.Survey
import com.hjpark.survey.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SurveyService(
    private val surveyRepository: SurveyRepository
) {

    /** 설문 조회 */
    @Transactional(readOnly = true)
    fun getFullSurvey(surveyId: Long): FullSurveyResponse {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("Survey not found with ID $surveyId") }

        return mapToFullSurveyResponse(survey)
    }

    /** 설문 목록 조회 */
    @Transactional(readOnly = true)
    fun getSurveys(keyword: String?): SurveyListDto {
        val surveys = if (keyword != null) {
            surveyRepository.findByNameContaining(keyword)
        } else {
            surveyRepository.findAllOrderByCreateTimeDesc()
        }

        val surveyDtos = surveys.map { survey ->
            SurveyDto(
                id = survey.id!!,
                name = survey.name,
                description = survey.description,
                createTime = survey.createTime!!,
                updateTime = survey.updateTime!!,
                questionCount = survey.questions.size
            )
        }

        return SurveyListDto(surveys = surveyDtos)
    }

    /** 설문 생성 */
    @Transactional
    fun createSurvey(request: CreateSurveyRequest): FullSurveyResponse {
        val survey = Survey(
            name = request.name,
            description = request.description
        )

        val questionEntities = request.questions.mapIndexed { index, questionRequest ->
            questionRequest.toEntity(survey, index.toShort())
        }
        survey.questions.addAll(questionEntities)

        val savedSurvey = surveyRepository.save(survey)
        return mapToFullSurveyResponse(savedSurvey)
    }

    /** 설문 수정 */
    @Transactional
    fun updateSurvey(surveyId: Long, request: UpdateSurveyRequest): FullSurveyResponse {
        val survey = getSurveyEntity(surveyId)
        // 설문 정보 업데이트
        survey.name = request.name
        survey.description = request.description

        // 질문 업데이트 로직
        updateQuestions(survey, request.questions)

        val updatedSurvey = surveyRepository.save(survey)
        return mapToFullSurveyResponse(updatedSurvey)
    }

    /** 설문 삭제 */
    @Transactional
    fun deleteSurvey(surveyId: Long) {
        val survey = getSurveyEntity(surveyId)
        surveyRepository.delete(survey)
    }

    // Private methods
    private fun mapToFullSurveyResponse(survey: Survey): FullSurveyResponse {
        return FullSurveyResponse(
            id = survey.id ?: throw IllegalStateException("Survey ID가 null입니다."),
            name = survey.name,
            description = survey.description,
            createTime = survey.createTime ?: throw IllegalStateException("Create time이 null입니다."),
            updateTime = survey.updateTime ?: throw IllegalStateException("Update time이 null입니다."),
            questions = survey.questions.map { question ->
                FullQuestionResponse(
                    id = question.id ?: throw IllegalStateException("Question ID가 null입니다."),
                    name = question.name,
                    description = question.description,
                    type = question.type.name,
                    required = question.required,
                    sequence = question.sequence,
                    options = question.options.map { option ->
                        FullOptionResponse(
                            id = option.id ?: throw IllegalStateException("Option ID가 null입니다."),
                            text = option.text,
                            sequence = option.sequence
                        )
                    }
                )
            }
        )
    }

    private fun updateQuestions(survey: Survey, questions: List<UpdateQuestionRequest>) {
        // 기존 질문 매핑 (ID 기준)
        val existingQuestions = survey.questions.associateBy { it.id }

        // 질문 목록 갱신
        survey.questions.clear()

        questions.forEachIndexed { index, questionRequest ->
            val question = if (questionRequest.id != null && existingQuestions.containsKey(questionRequest.id)) {
                // 기존 질문 업데이트
                val existing = existingQuestions[questionRequest.id]!!
                existing.apply {
                    name = questionRequest.name
                    description = questionRequest.description
                    type = QuestionType.valueOf(questionRequest.type)
                    required = questionRequest.required
                    sequence = questionRequest.sequence ?: index.toShort()
                    // 옵션 업데이트 로직 추가 가능
                }
            } else {
                // 새 질문 생성
                val createRequest = CreateQuestionRequest(
                    name = questionRequest.name,
                    description = questionRequest.description,
                    type = questionRequest.type,
                    required = questionRequest.required,
                    sequence = questionRequest.sequence ?: index.toShort(),
                    options = questionRequest.options?.map { option ->
                        CreateOptionRequest(
                            text = option.text,
                            sequence = option.sequence
                        )
                    }
                )
                createRequest.toEntity(survey, index.toShort())
            }

            survey.questions.add(question)
        }
    }

    private fun getSurveyEntity(surveyId: Long): Survey {
        return surveyRepository.findById(surveyId)
            .orElseThrow { NoSuchElementException("Survey not found with id: $surveyId") }
    }

    private fun CreateQuestionRequest.toEntity(survey: Survey, sequence: Short): Question {
        // Question 객체 생성
        val question = Question(
            survey = survey,
            sequence = sequence,
            name = this.name,
            description = this.description,
            type = QuestionType.valueOf(this.type),
            required = this.required
        )

        // 옵션 생성 및 연결
        this.options?.forEach { option ->
            val questionOption = QuestionOption(
                question = question,
                text = option.text,
                sequence = option.sequence
            )
            question.options.add(questionOption)
        }

        return question
    }

}
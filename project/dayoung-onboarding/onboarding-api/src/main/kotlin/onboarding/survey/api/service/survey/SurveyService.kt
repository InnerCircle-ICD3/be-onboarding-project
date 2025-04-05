package onboarding.survey.api.service.survey

import jakarta.transaction.Transactional
import onboarding.survey.api.model.request.CreateSurveyRequest
import onboarding.survey.api.model.request.QuestionRequest
import onboarding.survey.api.model.request.QuestionStatus
import onboarding.survey.api.model.request.UpdateSurveyRequest
import onboarding.survey.api.model.response.CreateSurveyResponse
import onboarding.survey.api.model.response.UpdateSurveyResponse
import onboarding.survey.data.survey.entity.Survey
import onboarding.survey.data.survey.entity.SurveyQuestion
import onboarding.survey.data.survey.entity.SurveyQuestionSelectList
import onboarding.survey.data.survey.repository.SurveyQuestionRepository
import onboarding.survey.data.survey.repository.SurveyQuestionSelectListRepository
import onboarding.survey.data.survey.repository.SurveyRepository
import onboarding.survey.data.survey.type.SurveyQuestionStatus
import onboarding.survey.data.survey.type.SurveyQuestionType
import org.springframework.stereotype.Service
import java.util.*

@Service
class SurveyService(
    private val surveyRepository: SurveyRepository,
    private val surveyQuestionRepository: SurveyQuestionRepository,
    private val surveyQuestionSelectListRepository: SurveyQuestionSelectListRepository
) {
    fun createSurvey(request: CreateSurveyRequest): CreateSurveyResponse {
        require(request.questions.size in 1..10) {
            "설문 항목은 1개 이상 10개 이하이어야 합니다."
        }

        val survey = Survey(
            surveyId = 0, // IDENTITY 전략이므로 더미 값 사용
            title = request.title,
            description = request.description,
            createdTime = Date(),
            updatedTime = Date()
        )

        val savedSurvey = surveyRepository.save(survey)

        // 각 질문 엔티티 생성 및 저장
        val questions = request.questions.mapIndexed { _, q ->
            SurveyQuestion(
                questionId = 0, // IDENTITY
                orderNumber = q.orderNumber,
                survey = savedSurvey,
                title = q.title,
                description = q.description,
                required = q.required,
                questionType = SurveyQuestionType.valueOf(q.type.name) // enum 매핑
            )
        }

        if (request.questions.any { it.type.name in listOf("SINGLE_SELECT", "MULTIPLE_SELECT") }) {
            // 선택형 질문에 대한 선택 리스트 저장
            questions.forEachIndexed { index, question ->
                if (request.questions[index].type.name in listOf("SINGLE_SELECT", "MULTIPLE_SELECT")) {
                    val selectList = request.questions[index].selectList.orEmpty().map {
                        SurveyQuestionSelectList(
                            selectId = 0, // IDENTITY
                            listValue = it,
                            question = question
                        )
                    }
                    surveyQuestionSelectListRepository.saveAll(selectList)
                }
            }
        }

        surveyQuestionRepository.saveAll(questions)

        return CreateSurveyResponse(surveyId = savedSurvey.surveyId)
    }

    @Transactional
    fun updateSurvey(surveyId: Int, request: UpdateSurveyRequest): UpdateSurveyResponse {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("설문이 존재하지 않습니다: $surveyId") }

        // 1. 설문 제목/설명 수정
        val updatedSurvey = survey.copy(
            title = request.title,
            description = request.description,
            updatedTime = Date()
        )
        surveyRepository.save(updatedSurvey)

        // 2. 기존 질문 조회 (orderNumber 기준 매핑)
        val existingQuestions = surveyQuestionRepository.findBySurveySurveyId(surveyId)
        val existingMap = existingQuestions.associateBy { it.orderNumber }
        request.questions.forEach { req ->
            when (req.questionStatus) {
                QuestionStatus.ADD -> handleAddQuestion(survey, req, existingMap)
                QuestionStatus.UPDATE -> handleUpdateQuestion(survey, req, existingMap)
                QuestionStatus.DELETE -> handleDeleteQuestion(req, existingMap)
                else -> error("정의되지 않은 상태입니다: ${req.questionStatus}")
            }
        }

        return UpdateSurveyResponse(surveyId)
    }

    private fun handleAddQuestion(survey: Survey, req: QuestionRequest, existingMap: Map<Int, SurveyQuestion>) {
        // 기존 항목이 10 이상이면 예외 발생
        if (existingMap.size >= 10) {
            throw IllegalStateException("질문은 최대 10개까지 등록할 수 있습니다.")
        }

        val newQuestion = SurveyQuestion(
            questionId = 0,
            orderNumber = req.orderNumber,
            survey = survey,
            title = req.title,
            description = req.description,
            required = req.required,
            questionType = req.type,
            questionStatus = SurveyQuestionStatus.ACTIVE,
            version = 1,
            lastModifiedDate = Date()
        )
        val saved = surveyQuestionRepository.save(newQuestion)
        saveSelectListIfNeeded(saved, req)
    }

    private fun handleUpdateQuestion(survey: Survey, req: QuestionRequest, existingMap: Map<Int, SurveyQuestion>) {
        val existing = existingMap[req.orderNumber]
            ?: throw IllegalStateException("수정 요청했으나 해당 orderNumber(${req.orderNumber})의 기존 질문이 없습니다.")

        // 기존 질문 비활성화
        existing.questionStatus = SurveyQuestionStatus.INACTIVE
        surveyQuestionRepository.save(existing)

        // 새 질문 등록
        val updated = SurveyQuestion(
            questionId = 0,
            orderNumber = req.orderNumber,
            survey = survey,
            title = req.title,
            description = req.description,
            required = req.required,
            questionType = req.type,
            questionStatus = SurveyQuestionStatus.ACTIVE,
            version = existing.version + 1,
            lastModifiedDate = Date()
        )
        val saved = surveyQuestionRepository.save(updated)
        saveSelectListIfNeeded(saved, req)
    }

    private fun handleDeleteQuestion(req: QuestionRequest, existingMap: Map<Int, SurveyQuestion>) {
        existingMap[req.orderNumber]?.let {
            it.questionStatus = SurveyQuestionStatus.INACTIVE
            surveyQuestionRepository.save(it)
        }
    }

    fun saveSelectListIfNeeded(question: SurveyQuestion, req: QuestionRequest) {
        if (req.type in listOf(SurveyQuestionType.SELECT, SurveyQuestionType.MULTI_SELECT)) {
            val options = req.selectList ?: emptyList()
            val selectEntities = options.map {
                SurveyQuestionSelectList(
                    selectId = 0,
                    listValue = it,
                    question = question
                )
            }
            surveyQuestionSelectListRepository.saveAll(selectEntities)
        }
    }
}
package org.survey.taehwanonboarding.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.survey.taehwanonboarding.api.dto.AnswerResponse
import org.survey.taehwanonboarding.api.dto.QuestionRequest
import org.survey.taehwanonboarding.api.dto.QuestionType
import org.survey.taehwanonboarding.api.dto.SurveyCreateRequest
import org.survey.taehwanonboarding.api.dto.SurveyCreateResponse
import org.survey.taehwanonboarding.api.dto.SurveyDetailResponse
import org.survey.taehwanonboarding.api.dto.SurveySummaryResponse
import org.survey.taehwanonboarding.domain.entity.survey.LongAnswerItem
import org.survey.taehwanonboarding.domain.entity.survey.MultiSelectionItem
import org.survey.taehwanonboarding.domain.entity.survey.ShortAnswerItem
import org.survey.taehwanonboarding.domain.entity.survey.SingleSelectionItem
import org.survey.taehwanonboarding.domain.entity.survey.Survey
import org.survey.taehwanonboarding.domain.entity.survey.SurveyItem
import org.survey.taehwanonboarding.domain.repository.survey.SurveyRepository
import java.time.format.DateTimeFormatter

@Service
class SurveyService(
    private val surveyRepository: SurveyRepository,
) {
    @Transactional
    fun createSurvey(request: SurveyCreateRequest): SurveyCreateResponse {
        validateSurveyRequest(request)

        val survey =
            Survey(
                title = request.title,
                description = request.description,
                status = Survey.SurveyStatus.DRAFT,
            )

        // 질문 항목 추가
        request.questions.forEach { questionRequest ->
            val item = createSurveyItem(questionRequest)
            survey.addItem(item)
        }

        // 저장
        val savedSurvey = surveyRepository.save(survey)

        // 응답 생성
        return SurveyCreateResponse(
            id = savedSurvey.id!!,
            title = savedSurvey.title,
            description = savedSurvey.description,
            status = savedSurvey.status.name,
            createdAt = savedSurvey.createdAt?.format(DateTimeFormatter.ISO_DATE_TIME) ?: "",
        )
    }

    private fun validateSurveyRequest(request: SurveyCreateRequest) {
        // 제목 필수 검증
        require(request.title.isNotBlank()) { "설문조사 제목은 필수입니다." }

        // 질문 개수 검증 (1-10개)
        require(request.questions.isNotEmpty() && request.questions.size <= 10) {
            "설문 항목은 1개에서 10개 사이여야 합니다."
        }

        // 각 질문 항목 유효성 검사
        request.questions.forEach { questionRequest ->
            validateQuestionRequest(questionRequest)
        }
    }

    private fun validateQuestionRequest(question: QuestionRequest) {
        // 제목은 필수값 검사
        require(question.title.isNotBlank()) {
            ValidationMessage.IS_TITLE_REQUIRED
        }

        // 항목별 유효성 검사
        when (question.type) {
            QuestionType.SINGLE_SELECTION, QuestionType.MULTI_SELECTION -> {
                requireNotNull(question.options) { ValidationMessage.IS_OPTION_REQUIRED }
                require(question.options.isNotEmpty()) { ValidationMessage.IS_MIN_OPTION_COUNT_ONE }

                // 다중 선택 항목인 경우 최소/최대 선택 개수 유효성 검사
                if (question.type == QuestionType.MULTI_SELECTION) {
                    question.minSelections?.let { min ->
                        question.maxSelections?.let { max ->
                            require(min <= max) { ValidationMessage.IS_MAX_LENGTH_THOUSAND }
                        }
                        require(min >= 0) { ValidationMessage.IS_MIN_OPTION_COUNT_ONE }
                    }
                }
            }

            QuestionType.SHORT_ANSWER, QuestionType.LONG_ANSWER -> {
                question.maxLength?.let { maxLength ->
                    require(maxLength > 0) { ValidationMessage.IS_REQUIRED_MIN_LENGTH_TEN }
                }
            }
        }
    }

    private fun createSurveyItem(question: QuestionRequest): SurveyItem = when (question.type) {
        QuestionType.SHORT_ANSWER ->
            ShortAnswerItem(
                title = question.title,
                description = question.description,
                required = question.required,
                orderNumber = question.orderNumber,
                maxLength = question.maxLength,
            )

        QuestionType.LONG_ANSWER ->
            LongAnswerItem(
                title = question.title,
                description = question.description,
                required = question.required,
                orderNumber = question.orderNumber,
                maxLength = question.maxLength,
            )

        QuestionType.SINGLE_SELECTION ->
            SingleSelectionItem(
                title = question.title,
                description = question.description,
                required = question.required,
                orderNumber = question.orderNumber,
                options = question.options?.toMutableList() ?: mutableListOf(),
            )

        QuestionType.MULTI_SELECTION ->
            MultiSelectionItem(
                title = question.title,
                description = question.description,
                required = question.required,
                orderNumber = question.orderNumber,
                options = question.options?.toMutableList() ?: mutableListOf(),
                minSelections = question.minSelections,
                maxSelections = question.maxSelections,
            )
    }

    @Transactional(readOnly = true)
    fun getSurveyById(id: Long): SurveyDetailResponse {
        val survey = surveyRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 설문조사입니다: ID=$id") }

        require(survey.status != Survey.SurveyStatus.ARCHIVED) {
            "삭제된 설문조사입니다: ID=$id"
        }

        // todo: 타입에 따른 factory builder 패턴을 적용 가능함
        return SurveyDetailResponse(
            id = survey.id!!,
            title = survey.title,
            description = survey.description,
            status = survey.status.name,
            createdAt = survey.createdAt?.format(DateTimeFormatter.ISO_DATE_TIME) ?: "",
            updatedAt = survey.updatedAt?.format(DateTimeFormatter.ISO_DATE_TIME) ?: "",
            items = survey.items.sortedBy { it.orderNumber }.map { item ->
                when (item) {
                    is ShortAnswerItem -> AnswerResponse(
                        id = item.id!!,
                        title = item.title,
                        description = item.description,
                        required = item.required,
                        orderNumber = item.orderNumber,
                        type = QuestionType.SHORT_ANSWER,
                        maxLength = item.maxLength,
                    )
                    is LongAnswerItem -> AnswerResponse(
                        id = item.id!!,
                        title = item.title,
                        description = item.description,
                        required = item.required,
                        orderNumber = item.orderNumber,
                        type = QuestionType.LONG_ANSWER,
                        maxLength = item.maxLength,
                    )
                    is SingleSelectionItem -> AnswerResponse(
                        id = item.id!!,
                        title = item.title,
                        description = item.description,
                        required = item.required,
                        orderNumber = item.orderNumber,
                        type = QuestionType.SINGLE_SELECTION,
                        options = item.options,
                    )
                    is MultiSelectionItem -> AnswerResponse(
                        id = item.id!!,
                        title = item.title,
                        description = item.description,
                        required = item.required,
                        orderNumber = item.orderNumber,
                        type = QuestionType.MULTI_SELECTION,
                        options = item.options,
                        minSelections = item.minSelections,
                        maxSelections = item.maxSelections,
                    )
                    else -> throw IllegalStateException("허용되지 않은 질문 유형입니다 : ${item.javaClass}")
                }
            },
        )
    }

    @Transactional(readOnly = true)
    fun getSurveys(status: Survey.SurveyStatus? = null): List<SurveySummaryResponse> {
        val surveys = if (status != null) {
            surveyRepository.findAllByStatus(status)
        } else {
            surveyRepository.findAllByStatusNot(Survey.SurveyStatus.ARCHIVED)
        }

        return surveys.map { survey ->
            SurveySummaryResponse(
                id = survey.id!!,
                title = survey.title,
                description = survey.description,
                status = survey.status.name,
                itemCount = survey.items.size,
                createdAt = survey.createdAt?.format(DateTimeFormatter.ISO_DATE_TIME) ?: "",
            )
        }
    }
}
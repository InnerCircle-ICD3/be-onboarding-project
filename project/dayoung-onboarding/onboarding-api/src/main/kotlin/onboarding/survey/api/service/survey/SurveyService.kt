package onboarding.survey.api.service.survey

import onboarding.survey.api.model.request.CreateSurveyRequest
import onboarding.survey.api.model.response.CreateSurveyResponse
import onboarding.survey.data.survey.entity.Survey
import onboarding.survey.data.survey.entity.SurveyQuestion
import onboarding.survey.data.survey.repository.SurveyQuestionRepository
import onboarding.survey.data.survey.repository.SurveyRepository
import onboarding.survey.data.survey.type.SurveyQuestionType
import org.springframework.stereotype.Service
import java.util.*

@Service
class SurveyService(
    private val surveyRepository: SurveyRepository,
    private val surveyQuestionRepository: SurveyQuestionRepository,
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

        surveyQuestionRepository.saveAll(questions)

        return CreateSurveyResponse(surveyId = savedSurvey.surveyId)
    }
}
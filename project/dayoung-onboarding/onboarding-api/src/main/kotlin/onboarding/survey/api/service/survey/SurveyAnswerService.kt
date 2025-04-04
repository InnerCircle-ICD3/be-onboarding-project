package onboarding.survey.api.service.survey

import jakarta.transaction.Transactional
import onboarding.survey.api.model.request.AnswerSurveyRequest
import onboarding.survey.api.model.response.AnswerDetail
import onboarding.survey.api.model.response.AnswerResponse
import onboarding.survey.api.model.response.AnswerSurveyResponse
import onboarding.survey.api.model.response.GetAnswersResponse
import onboarding.survey.data.survey.entity.SurveyAnswer
import onboarding.survey.data.survey.entity.SurveyAnswerDetail
import onboarding.survey.data.survey.repository.*
import onboarding.survey.data.survey.type.SurveyQuestionStatus
import onboarding.survey.data.survey.type.SurveyQuestionType
import onboarding.survey.data.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Date

@Service
class SurveyAnswerService (
    private val surveyRepository: SurveyRepository,
    private val surveyQuestionRepository: SurveyQuestionRepository,
    private val surveyQuestionSelectListRepository: SurveyQuestionSelectListRepository,
    private val surveyAnswerRepository: SurveyAnswerRepository,
    private val surveyAnswerDetailRepository: SurveyAnswerDetailRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun answerSurvey(surveyId: Int, request: AnswerSurveyRequest): AnswerSurveyResponse {
        val survey = surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 설문입니다.") }

        val user = userRepository.findById(request.userId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 사용자입니다.") }

        val questions = surveyQuestionRepository.findBySurveySurveyId(surveyId)
            .filter { it.questionStatus == SurveyQuestionStatus.ACTIVE }

        val questionMap = questions.associateBy { it.questionId }

        request.answers.forEach { item ->
            val question = questionMap[item.questionId]
                ?: throw IllegalArgumentException("해당 (${item.questionId})의 질문이 없습니다.")

            // 필수 여부 확인
            if (question.required == true && item.answer.isBlank()) {
                throw IllegalArgumentException("필수 항목 '${question.title}'에 응답이 필요합니다.")
            }

            // 질문 타입에 따른 유효성 확인
            when (question.questionType) {
                SurveyQuestionType.SHORT_TEXT,
                SurveyQuestionType.LONG_TEXT -> {
                }

                SurveyQuestionType.SELECT -> {
                    val validOptions = surveyQuestionSelectListRepository.findByQuestionQuestionId(question.questionId)
                        .map { it.listValue }
                    if (item.answer !in validOptions) {
                        throw IllegalArgumentException("질문 '${question.title}'의 응답이 선택지와 일치하지 않습니다.")
                    }
                }

                SurveyQuestionType.MULTI_SELECT -> {
                    val inputList = item.answer.split(",").map { it.trim() }
                    val validOptions = surveyQuestionSelectListRepository.findByQuestionQuestionId(question.questionId)
                        .map { it.listValue }
                    if (!inputList.all { it in validOptions }) {
                        throw IllegalArgumentException("다중 선택 응답이 유효하지 않습니다.")
                    }
                }
            }
        }

        // SurveyAnswer 저장
        val answerEntity = SurveyAnswer(
            answerId = 0,
            user = user,
            survey = survey,
            createdTime = Date(),
            updatedTime = Date()
        )
        val savedAnswer = surveyAnswerRepository.save(answerEntity)

        // SurveyAnswerDetail 저장
        val answerDetails = request.answers.map { item ->
            val question = questionMap[item.questionId]!!
            SurveyAnswerDetail(
                answerDetailId = 0,
                answerId = savedAnswer,
                question = question,
                answer = item.answer
            )
        }

        surveyAnswerDetailRepository.saveAll(answerDetails)

        return AnswerSurveyResponse(
            surveyId = surveyId,
            answerId = savedAnswer.answerId
        )
    }

    fun getSurveyAnswers(surveyId: Int): GetAnswersResponse {
        surveyRepository.findById(surveyId)
            .orElseThrow { IllegalArgumentException("설문이 존재하지 않습니다.") }

        val questions = surveyQuestionRepository.findBySurveySurveyId(surveyId)
            .filter { it.questionStatus == SurveyQuestionStatus.ACTIVE }
            .associateBy { it.questionId }

        val answers = surveyAnswerRepository.findBySurveySurveyId(surveyId)
        val answerDetails = surveyAnswerDetailRepository.findByAnswerIdIn(answers.map { it.answerId })

        // 그룹핑: answerId → List<Detail>
        val detailGrouped = answerDetails.groupBy { it.answerId.answerId }

        val responseItems = answers.map { answer ->
            val details = detailGrouped[answer.answerId] ?: emptyList()

            AnswerResponse(
                answerId = answer.answerId,
                userId = answer.user.userId,
                answerAt = answer.createdTime ?: Date(),
                answers = details.mapNotNull { detail ->
                    val question = questions[detail.question.questionId]
                    question?.let {
                        AnswerDetail(
                            questionId = it.questionId,
                            questionName = it.title,
                            answer = detail.answer.orEmpty()
                        )
                    }
                }.sortedBy { it.questionId },
            )
        }

        return GetAnswersResponse(
            surveyId = surveyId,
            answers = responseItems
        )
    }
}
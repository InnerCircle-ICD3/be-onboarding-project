package onboarding.survey.api.model.response

import java.util.*

data class GetAnswersResponse(
    val surveyId: Int,
    val answers: List<AnswerResponse>
)

data class AnswerResponse(
    val answerId: Int,
    val userId: String,
    val answerAt: Date,
    val answers: List<AnswerDetail>
)

data class AnswerDetail(
    val questionId: Int,
    val questionName: String,
    val answer: Any
)
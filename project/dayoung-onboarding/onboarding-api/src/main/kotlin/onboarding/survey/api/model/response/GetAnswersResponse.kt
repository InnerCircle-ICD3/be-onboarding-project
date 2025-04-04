package onboarding.survey.api.model.response

data class GetAnswersResponse(
    val answers: List<AnswerResponse>
)

data class AnswerResponse(
    val responseId: Int,
    val answers: List<AnswerDetail>
)

data class AnswerDetail(
    val questionId: Int,
    val questionName: String,
    val answer: Any
)
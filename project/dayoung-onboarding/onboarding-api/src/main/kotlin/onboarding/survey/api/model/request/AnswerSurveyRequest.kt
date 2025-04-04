package onboarding.survey.api.model.request

data class AnswerSurveyRequest(
    val userId: Int,
    val answers: List<Answer>
)

data class Answer(
    val questionId: Int,
    val answer: String
)
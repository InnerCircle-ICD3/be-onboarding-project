package onboarding.survey.api.model.request

data class AnswerSurveyRequest(
    val answers: List<Answer>
)

data class Answer(
    val questionId: Int,
    val answer: Any
)
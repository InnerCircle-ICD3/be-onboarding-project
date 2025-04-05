package onboarding.survey.api.model.request

data class UpdateSurveyRequest(
    val title: String,
    val description: String,
    val questions: List<QuestionRequest>
)

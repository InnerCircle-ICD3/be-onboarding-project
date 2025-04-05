package onboarding.survey.api.model.request

import onboarding.survey.data.survey.type.SurveyQuestionType

data class CreateSurveyRequest(
    val title: String,
    val description: String,
    val questions: List<QuestionRequest>
)

data class QuestionRequest(
    var orderNumber: Int,
    val title: String,
    val description: String,
    val type: SurveyQuestionType,
    val required: Boolean,
    val questionStatus:QuestionStatus? = QuestionStatus.ADD,
    val options: List<String>? = null, // 선택형일 때만
    val selectList: List<String>? = null // 선택형일 때만
)
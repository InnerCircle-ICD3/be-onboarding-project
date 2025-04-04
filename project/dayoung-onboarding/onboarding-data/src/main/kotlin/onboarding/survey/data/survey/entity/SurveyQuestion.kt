package onboarding.survey.data.survey.entity

import jakarta.persistence.*
import onboarding.survey.data.survey.type.SurveyQuestionStatus
import onboarding.survey.data.survey.type.SurveyQuestionType
import java.util.Date

@Entity
@Table(name = "SURVEY_QUESTION")
data class SurveyQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val questionId: Int,
    var orderNumber: Int,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    val title: String,
    val description: String? = "",
    val required: Boolean? = false,

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    val questionType: SurveyQuestionType,

    val questionStatus: SurveyQuestionStatus = SurveyQuestionStatus.ACTIVE,
    val lastModifiedDate: Date = Date(),
    val version: Int = 1
)

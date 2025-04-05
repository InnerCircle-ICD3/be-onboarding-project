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

    var title: String,
    var description: String? = "",
    var required: Boolean? = false,

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    val questionType: SurveyQuestionType,

    var questionStatus: SurveyQuestionStatus = SurveyQuestionStatus.ACTIVE,
    var lastModifiedDate: Date = Date(),
    var version: Int = 1
)

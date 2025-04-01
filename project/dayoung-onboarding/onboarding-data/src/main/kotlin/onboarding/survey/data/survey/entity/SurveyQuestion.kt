package onboarding.survey.data.survey.entity

import jakarta.persistence.*
import onboarding.survey.data.survey.type.SurveyQuestionType

@Entity
@Table(name = "SURVEY_QUESTION")
data class SurveyQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val questionId: Int,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    val title: String,
    val description: String? = "",
    val required: Boolean? = false,

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    val questionType: SurveyQuestionType
)

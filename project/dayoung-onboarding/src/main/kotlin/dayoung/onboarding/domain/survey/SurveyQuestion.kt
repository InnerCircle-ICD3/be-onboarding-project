package dayoung.onboarding.domain.survey

import jakarta.persistence.*

@Entity
@Table(name = "SURVEY_QUESTION")
data class SurveyQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val questionId: Int = 0,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey? = null,

    val title: String? = null,
    val desc: String? = null,
    val required: Boolean? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    val questionType: SurveyQuestionType? = null
)

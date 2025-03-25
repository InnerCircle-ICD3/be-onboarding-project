package dayoung.onboarding.domain

import jakarta.persistence.*

@Entity
@Table(name = "SURVEY_ANSWER")
data class SurveyAnswer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val answerId: Int = 0,

    val answer: String? = null,

    @ManyToOne
    @JoinColumn(name = "question_id")
    val question: SurveyQuestion? = null,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey? = null
)

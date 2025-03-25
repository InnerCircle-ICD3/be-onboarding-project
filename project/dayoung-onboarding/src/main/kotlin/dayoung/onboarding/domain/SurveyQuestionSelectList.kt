package dayoung.onboarding.domain

import jakarta.persistence.*

@Entity
@Table(name = "SURVEY_QUESTION_SELECT_LIST")
data class SurveyQuestionSelectList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val selectId: Int = 0,

    val listValue: String? = null,

    @ManyToOne
    @JoinColumn(name = "question_id")
    val question: SurveyQuestion? = null
)

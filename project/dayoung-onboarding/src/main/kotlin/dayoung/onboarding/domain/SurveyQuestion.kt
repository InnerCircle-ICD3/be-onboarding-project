package dayoung.onboarding.domain

import jakarta.persistence.*

@Entity
@Table(name = "SURVEY_QUESTION")
data class SurveyQuestion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val questionId: Int = 0,

    val title: String? = null,
    val questionIndex: Int? = null,
    val desc: String? = null,
    val required: Boolean? = null,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey? = null,

    val questionType: String? = null
)

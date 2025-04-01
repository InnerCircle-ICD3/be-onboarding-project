package onboarding.survey.data.survey.entity

import jakarta.persistence.*

@Entity
@Table(name = "SURVEY_QUESTION_SELECT_LIST")
data class SurveyQuestionSelectList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val selectId: Int,

    val listValue: String = "",

    @ManyToOne
    @JoinColumn(name = "question_id")
    val question: SurveyQuestion
)

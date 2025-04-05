package onboarding.survey.data.survey.entity

import jakarta.persistence.*

@Entity
@Table(name = "SURVEY_ANSWER_DETAIL")
data class SurveyAnswerDetail (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val answerDetailId: Int,

    @ManyToOne
    @JoinColumn(name = "answer_id")
    val answerId: SurveyAnswer,

    @ManyToOne
    @JoinColumn(name = "question_id")
    val question: SurveyQuestion,

    val answer: String? = "",
)

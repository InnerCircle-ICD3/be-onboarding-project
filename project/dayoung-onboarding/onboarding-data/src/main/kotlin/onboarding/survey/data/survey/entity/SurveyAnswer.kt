package onboarding.survey.data.survey.entity

import onboarding.survey.data.user.entity.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*

@Entity
@Table(name = "SURVEY_ANSWER")
data class SurveyAnswer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val answerId: Int,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val createdTime: Date?,

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    val updatedTime: Date?
)

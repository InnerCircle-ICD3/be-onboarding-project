package dayoung.onboarding.domain.survey

import dayoung.onboarding.domain.user.User
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "SURVEY")
data class Survey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val surveyId: Int = 0,

    val title: String? = null,
    val desc: String? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null,

    @Temporal(TemporalType.TIMESTAMP)
    val createdTime: Date? = null,

    @Temporal(TemporalType.TIMESTAMP)
    val updatedTime: Date? = null
)
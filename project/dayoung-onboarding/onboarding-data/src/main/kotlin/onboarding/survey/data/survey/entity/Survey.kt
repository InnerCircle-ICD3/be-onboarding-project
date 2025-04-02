package onboarding.survey.data.survey.entity

import onboarding.survey.data.user.entity.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*

@Entity
@Table(name = "SURVEY")
data class Survey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val surveyId: Int,

    val title: String,
    val description: String? = "",

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null,

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val createdTime: Date?,

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    val updatedTime: Date?
)
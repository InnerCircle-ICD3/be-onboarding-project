package onboarding.survey.data.user.entity

import jakarta.persistence.*
import onboarding.survey.data.user.type.UserType

@Entity
@Table(name = "USER")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    val userId: String,
    val name: String? = "",
    var email: String,
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    val userType: UserType  // Admin, User 구분용
)
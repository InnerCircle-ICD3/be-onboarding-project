package dayoung.onboarding.domain.user

import jakarta.persistence.*

@Entity
@Table(name = "USER")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val name: String? = null,
    val password: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    val userType: UserType? = null  // Admin, User 구분용
)
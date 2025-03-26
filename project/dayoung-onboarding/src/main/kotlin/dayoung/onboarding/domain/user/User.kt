package dayoung.onboarding.domain.user

import jakarta.persistence.*

@Entity
@Table(name = "USER")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val name: String? = null,
    val userKey: String? = null,
    val password: String? = null,
    val userType: String? = null
)
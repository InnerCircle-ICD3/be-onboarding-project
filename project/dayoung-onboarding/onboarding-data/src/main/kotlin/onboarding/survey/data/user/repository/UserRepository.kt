package onboarding.survey.data.user.repository

import onboarding.survey.data.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
}
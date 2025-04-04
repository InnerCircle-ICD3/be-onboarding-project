package onboarding.survey.data.survey.repository

import onboarding.survey.data.survey.entity.SurveyAnswer
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyAnswerRepository : JpaRepository<SurveyAnswer, Int> {
}
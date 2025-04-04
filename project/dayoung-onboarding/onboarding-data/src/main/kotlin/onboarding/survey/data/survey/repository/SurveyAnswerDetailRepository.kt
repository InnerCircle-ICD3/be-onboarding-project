package onboarding.survey.data.survey.repository

import onboarding.survey.data.survey.entity.SurveyAnswerDetail
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyAnswerDetailRepository : JpaRepository<SurveyAnswerDetail, Int> {
}
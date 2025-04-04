package onboarding.survey.data.survey.repository

import onboarding.survey.data.survey.entity.SurveyQuestionSelectList
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyQuestionSelectListRepository : JpaRepository<SurveyQuestionSelectList, Int> {
}
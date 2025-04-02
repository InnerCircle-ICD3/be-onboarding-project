package onboarding.survey.data.survey.repository

import onboarding.survey.data.survey.entity.SurveyQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyQuestionRepository : JpaRepository<SurveyQuestion, Int>
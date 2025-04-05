package onboarding.survey.data.survey.repository

import onboarding.survey.data.survey.entity.Survey
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyRepository : JpaRepository<Survey, Int>
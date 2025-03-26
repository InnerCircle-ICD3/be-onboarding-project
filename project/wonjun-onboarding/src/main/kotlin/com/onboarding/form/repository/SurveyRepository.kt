package com.onboarding.form.repository

import com.onboarding.form.domain.Survey
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyRepository : JpaRepository<Survey,Long> {}
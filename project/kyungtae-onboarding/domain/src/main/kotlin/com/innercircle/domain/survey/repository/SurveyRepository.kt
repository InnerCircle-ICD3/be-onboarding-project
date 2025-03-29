package com.innercircle.domain.survey.repository

import com.innercircle.survey.entity.Survey
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyRepository : JpaRepository<Survey, Long>
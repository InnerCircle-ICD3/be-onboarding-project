package com.example.doohyeononboarding.domain.servey

import org.springframework.data.jpa.repository.JpaRepository

interface SurveyRepository : JpaRepository<Survey, Long> {

}

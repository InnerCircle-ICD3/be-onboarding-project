package com.chosseang.seonghunonboarding.repository

import com.chosseang.seonghunonboarding.entity.Survey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SurveyRepository : JpaRepository<Survey, Long>

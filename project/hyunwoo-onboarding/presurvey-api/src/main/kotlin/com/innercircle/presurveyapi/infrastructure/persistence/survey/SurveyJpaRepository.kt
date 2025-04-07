package com.innercircle.presurveyapi.infrastructure.persistence.survey

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SurveyJpaRepository : JpaRepository<SurveyJpaEntity, UUID>
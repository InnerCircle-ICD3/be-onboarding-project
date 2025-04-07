package com.innercircle.presurveyapi.config

import com.innercircle.presurveyapi.domain.survey.repository.SurveyRepository
import com.innercircle.presurveyapi.infrastructure.adapter.survey.SurveyMapper
import com.innercircle.presurveyapi.infrastructure.adapter.survey.SurveyPersistenceAdapter
import com.innercircle.presurveyapi.infrastructure.persistence.survey.SurveyJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PersistenceAdapterConfig {
    @Bean
    fun surveyRepository(
        surveyJpaRepository: SurveyJpaRepository,
        surveyMapper: SurveyMapper
    ): SurveyRepository {
        return SurveyPersistenceAdapter(surveyJpaRepository, surveyMapper)
    }
}
package com.innercircle.presurveyapi.config

import com.innercircle.presurveyapi.application.survey.command.CreateSurveyUseCase
import com.innercircle.presurveyapi.domain.survey.repository.SurveyRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {

    @Bean
    fun createSurveyUseCase(
        surveyRepository: SurveyRepository
    ): CreateSurveyUseCase {
        return CreateSurveyUseCase(surveyRepository)
    }
}
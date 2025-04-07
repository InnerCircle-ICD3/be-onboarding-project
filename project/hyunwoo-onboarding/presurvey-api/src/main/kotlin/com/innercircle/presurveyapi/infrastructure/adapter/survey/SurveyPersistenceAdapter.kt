package com.innercircle.presurveyapi.infrastructure.adapter.survey

import com.innercircle.presurveyapi.domain.survey.model.Survey
import com.innercircle.presurveyapi.domain.survey.repository.SurveyRepository
import com.innercircle.presurveyapi.infrastructure.persistence.survey.SurveyJpaRepository

class SurveyPersistenceAdapter(
    private val surveyJpaRepository: SurveyJpaRepository,
    private val surveyMapper: SurveyMapper
) : SurveyRepository {

    override fun save(survey: Survey): Survey {
        val entity = surveyMapper.toEntity(survey)
        val saved = surveyJpaRepository.save(entity)
        return surveyMapper.toDomain(saved)
    }
}
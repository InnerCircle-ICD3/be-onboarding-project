package org.survey.infra.persistence

import org.survey.domain.survey.model.ItemOption
import org.survey.domain.survey.model.Survey
import org.survey.domain.survey.model.SurveyItem
import org.survey.infra.persistence.entity.survey.ItemOptionEntity
import org.survey.infra.persistence.entity.survey.SurveyEntity
import org.survey.infra.persistence.entity.survey.SurveyItemEntity

fun Survey.toEntity() =
    SurveyEntity(
        title = this.title,
        description = this.description,
    )

fun SurveyEntity.toDomain() =
    Survey(
        id = this.id,
        title = this.title,
        description = this.description,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )

fun SurveyItem.toEntity() =
    SurveyItemEntity(
        surveyId = this.surveyId,
        title = this.title,
        description = this.description,
        inputType = this.inputType,
        isRequired = this.isRequired,
    )

fun SurveyItemEntity.toDomain() =
    SurveyItem(
        id = this.id,
        surveyId = this.surveyId,
        title = this.title,
        description = this.description,
        inputType = this.inputType,
        isRequired = this.isRequired,
        isDeleted = this.isDeleted,
    )

fun ItemOption.toEntity() =
    ItemOptionEntity(
        surveyItemId = this.surveyItemId,
        value = this.value,
    )

fun ItemOptionEntity.toDomain() =
    ItemOption(
        id = this.id,
        surveyItemId = this.surveyItemId,
        value = this.value,
    )

package com.innercircle.presurveyapi.infrastructure.adapter.survey

import com.innercircle.presurveyapi.domain.survey.model.Question
import com.innercircle.presurveyapi.domain.survey.model.QuestionOption
import com.innercircle.presurveyapi.domain.survey.model.QuestionType
import com.innercircle.presurveyapi.domain.survey.model.Survey
import com.innercircle.presurveyapi.infrastructure.persistence.survey.QuestionJpaEntity
import com.innercircle.presurveyapi.infrastructure.persistence.survey.QuestionOptionJpaEntity
import com.innercircle.presurveyapi.infrastructure.persistence.survey.SurveyJpaEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class SurveyMapper {

    fun toEntity(domain: Survey): SurveyJpaEntity {
        val survey = SurveyJpaEntity(
            id = null,
            title = domain.title,
            description = domain.description,
            questions = mutableListOf()
        )

        val questions = domain.questions.map { question ->
            val options = question.options.map { option ->
                QuestionOptionJpaEntity(
                    id = null,
                    text = option.text
                )
            }.toMutableList()

            QuestionJpaEntity(
                id = null,
                survey = survey,
                title = question.title,
                description = question.description,
                type = question.type.name,
                required = question.required,
                options = options
            )
        }

        survey.questions.addAll(questions)
        return survey
    }

    fun toDomain(entity: SurveyJpaEntity): Survey {
        return Survey(
            id = null,
            title = entity.title,
            description = entity.description,
            questions = entity.questions.map { question: QuestionJpaEntity ->
                Question(
                    id = null,
                    title = question.title,
                    description = question.description,
                    type = QuestionType.valueOf(question.type),
                    required = question.required,
                    options = question.options.map { option ->
                        QuestionOption(id = option.id ?: UUID.randomUUID(), text = option.text)
                    }
                )
            }
        )
    }
}
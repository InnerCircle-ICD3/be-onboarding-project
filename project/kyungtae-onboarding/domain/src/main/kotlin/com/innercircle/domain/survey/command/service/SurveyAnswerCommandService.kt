package com.innercircle.domain.survey.command.service

import com.innercircle.common.CommandService
import com.innercircle.domain.survey.command.dto.SurveyAnswerCreateCommand
import com.innercircle.domain.survey.repository.SurveyAnswerRepository
import com.innercircle.survey.entity.SurveyAnswer
import com.innercircle.survey.entity.SurveyQuestion

@CommandService
class SurveyAnswerCommandService(
    private val surveyAnswerRepository: SurveyAnswerRepository
) {
    fun create(surveyQuestion: SurveyQuestion, command: SurveyAnswerCreateCommand): SurveyAnswer {
        val surveyAnswer = SurveyAnswer.of(surveyQuestion, command)

        return surveyAnswerRepository.save(surveyAnswer)
    }

}
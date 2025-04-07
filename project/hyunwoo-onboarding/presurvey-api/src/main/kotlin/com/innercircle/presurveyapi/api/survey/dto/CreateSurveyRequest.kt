package com.innercircle.presurveyapi.api.survey.dto

import com.innercircle.presurveyapi.application.survey.command.CreateQuestionCommand
import com.innercircle.presurveyapi.application.survey.command.CreateSurveyCommand
import com.innercircle.presurveyapi.domain.survey.model.QuestionType

data class CreateSurveyRequest(
    val title: String, // 설문조사 이름
    val description: String, //설문조사 설명
    val questions: List<CreateQuestionRequest> //설문 받을 항목
) {
    fun toCommand(): CreateSurveyCommand = CreateSurveyCommand(
        title = title,
        description = description,
        questions = questions.map { it.toCommand() }
    )
}

data class CreateQuestionRequest(
    val title: String, //항목 이름
    val description: String, //항목 설명
    val type: QuestionType, //항목 이름 형태
    val required: Boolean, //항목 필수 여부
    val options: List<String> = emptyList()
) {
    fun toCommand(): CreateQuestionCommand = CreateQuestionCommand(
        title = title,
        description = description,
        type = type,
        required = required,
        options = options
    )
}

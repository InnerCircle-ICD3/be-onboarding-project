package org.survey.domain.response.model

class TextAnswer(
    override val surveyResponseId: Long = 0,
    override val surveyItemId: Long,
    val value: String,
) : Answer

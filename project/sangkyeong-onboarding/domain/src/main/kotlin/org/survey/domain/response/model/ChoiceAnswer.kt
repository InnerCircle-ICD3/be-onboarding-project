package org.survey.domain.response.model

class ChoiceAnswer(
    override val surveyResponseId: Long = 0,
    override val surveyItemId: Long,
    val itemOptionIds: Set<Long>,
) : Answer

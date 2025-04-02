package com.onboarding.form.domain

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


class SurveyTest {
    @Test
    @DisplayName("설문조사는 10개 이상의 질문을 가지지 못한다.")
    fun surveysCanNotHaveExceedNumberOfQuestions() {
        val survey = Survey.of(
            title = "test",
            description = "test"
        )
        val question = ShortQuestion(title = "test", description = "test", isRequired = true)

        repeat(10) { survey.addQuestion(question) }

        assertThrows<IllegalStateException> { survey.addQuestion(question) }
    }

    @Test
    @DisplayName("설문조사를 수정할 경우 version 값이 1씩 증가한다")
    fun increaseSequenceWhenSurveyUpdated() {
        val survey = Survey.of(
            title = "test",
            description = "test"
        )
        val question = ShortQuestion(title = "test", description = "test", isRequired = true)
        survey.addQuestion(question)

        assertEquals(survey.currentVersion.version, 1)

        survey.update("newTitle", "newDescription", listOf(question))
        assertEquals(survey.currentVersion.version, 2)
    }
}
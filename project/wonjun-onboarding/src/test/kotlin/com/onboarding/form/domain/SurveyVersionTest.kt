package com.onboarding.form.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.util.NoSuchElementException

class SurveyVersionTest{
    @Test
    fun checkNotThrowExceptionByValidAnswer(){
        val multiselectQuestion = MultiSelectQuestion(
            id = 1,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
            answerList = listOf("testAnswer1", "testAnswer2")
        )
        val singleSelectQuestion = SingleSelectQuestion(
            id = 2,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
            answerList = listOf("testAnswer1", "testAnswer2")
        )
        val longQuestion = LongQuestion(
            id = 3,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
        )
        val shortQuestion = ShortQuestion(
            id = 4,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
        )

        val survey = SurveyVersion(
            version = 0,
            questions =  mutableListOf(multiselectQuestion, singleSelectQuestion, longQuestion, shortQuestion)
        )

        val validAnswer = listOf(
            SelectAnswer(0, multiselectQuestion.id, listOf(multiselectQuestion.answerList.first())),
            SelectAnswer(0, singleSelectQuestion.id, listOf(multiselectQuestion.answerList.first())),
            InsertAnswer(0, longQuestion.id, "longtestContent1"),
            InsertAnswer(0, shortQuestion.id, "shorttestContent1")
        )

        assertDoesNotThrow { survey.checkValid(validAnswer) }
    }

    @Test
    fun checkThrowExceptionByInvalidAnswer(){
        val multiselectQuestion = MultiSelectQuestion(
            id = 1,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
            answerList = listOf("testAnswer1", "testAnswer2")
        )
        val singleSelectQuestion = SingleSelectQuestion(
            id = 2,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
            answerList = listOf("testAnswer1", "testAnswer2")
        )
        val longQuestion = LongQuestion(
            id = 3,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
        )
        val shortQuestion = ShortQuestion(
            id = 4,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
        )

        val survey = SurveyVersion(
            version = 0,
            questions =  mutableListOf(multiselectQuestion, singleSelectQuestion, longQuestion, shortQuestion)
        )

        val invalidSizeAnswers = listOf(
            SelectAnswer(0, multiselectQuestion.id, listOf(multiselectQuestion.answerList.first())),
            SelectAnswer(0, singleSelectQuestion.id, listOf(multiselectQuestion.answerList.first())),
            InsertAnswer(0, shortQuestion.id, "shorttestContent1")
        )

        assertThrows<NoSuchElementException> { survey.checkValid(invalidSizeAnswers) }
    }
}
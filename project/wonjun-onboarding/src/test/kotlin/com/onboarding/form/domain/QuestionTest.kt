package com.onboarding.form.domain


import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows


class QuestionTest {
    @Test
    fun shortAndLongQuestionCheckValidAnswer() {
        val longQuestion = LongQuestion(
            id = 1,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
        )
        val shortQuestion = ShortQuestion(
            id = 2,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
        )

        val longAnswer = InsertAnswer(0, longQuestion.id, "testContent")
        val shortAnswer = InsertAnswer(0, shortQuestion.id, "testContent")

        assertDoesNotThrow { longQuestion.checkValid(longAnswer) }
        assertDoesNotThrow { shortQuestion.checkValid(shortAnswer) }
    }

    @Test
    fun shortAndLongQuestionCheckInvalidAnswer() {
        val longQuestion = LongQuestion(
            id = 1,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
        )
        val shortQuestion = ShortQuestion(
            id = 2,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
        )

        val invalidTypeAnswer = SelectAnswer(0, 0, listOf("testContent"))

        assertThrows<IllegalStateException> { longQuestion.checkValid(invalidTypeAnswer) }
        assertThrows<IllegalStateException> { shortQuestion.checkValid(invalidTypeAnswer) }
    }

    @Test
    fun singleAndMultiSelectQuestionCheckValidAnswer(){
        val singleSelectQuestion = SingleSelectQuestion(
            id = 1,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
            answerList = listOf("answer1", "answer2")
        )
        val multiSelectQuestion = MultiSelectQuestion(
            id = 2,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
            answerList = listOf("answer1", "answer2")
        )

        val singleSelectAnswer = SelectAnswer(0, singleSelectQuestion.id, listOf(singleSelectQuestion.answerList.first()))
        val multiSelectAnswer = SelectAnswer(0, multiSelectQuestion.id, multiSelectQuestion.answerList)

        assertDoesNotThrow { singleSelectQuestion.checkValid(singleSelectAnswer) }
        assertDoesNotThrow { multiSelectQuestion.checkValid(multiSelectAnswer) }
    }

    @Test
    fun singleSelectQuestionCheckInvalidAnswer(){
        val singleSelectQuestion = SingleSelectQuestion(
            id = 1,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
            answerList = listOf("answer1", "answer2")
        )

        val singleSelectAnswer = SelectAnswer(0, singleSelectQuestion.id, singleSelectQuestion.answerList)

        assertThrows<IllegalArgumentException> { singleSelectQuestion.checkValid(singleSelectAnswer) }
    }

    @Test
    fun multiSelectQuestionCheckInvalidAnswer(){
        val multiSelectQuestion = MultiSelectQuestion(
            id = 1,
            title = "testTitle",
            description = "testDescription",
            isRequired = true,
            answerList = listOf("answer1", "answer2")
        )

        val singleSelectAnswer = SelectAnswer(0, multiSelectQuestion.id, listOf("answer1", "answer2", "answer3"))

        assertThrows<IllegalArgumentException> { multiSelectQuestion.checkValid(singleSelectAnswer) }
    }
}
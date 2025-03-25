package com.innercircle.survey.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class SurveyTest {
    private val now = LocalDateTime.now()

    @Test
    fun `Survey는 of 메서드로 생성되고 기본 상태는 READY이다`() {
        // given
        val now = LocalDateTime.now()
        val survey = Survey.of("설문 제목", "설문 설명", now, now.plusDays(1))

        // when & then
        assertThat(survey.context.name).isEqualTo("설문 제목")
        assertThat(survey.context.description).isEqualTo("설문 설명")
        assertThat(survey.status).isEqualTo(SurveyStatus.READY)
    }

    @Test
    fun `startAt이 endAt보다 늦으면 예외가 발생한다`() {
        // given
        val start = LocalDateTime.of(2025, 1, 2, 10, 0)
        val end = LocalDateTime.of(2025, 1, 1, 10, 0)

        // when & then
        val exception = assertThrows<IllegalArgumentException> {
            Survey.of("제목", "설명", start, end)
        }

        assertThat(exception.message).isEqualTo("startAt must be before endAt")
    }

    @Test
    fun `READY 상태일 때 start()를 호출하면 IN_PROGRESS로 전이된다`() {
        // given
        val survey = Survey.of("title", "desc", now, now.plusDays(1))

        // when
        survey.start()

        // then
        assertThat(survey.status).isEqualTo(SurveyStatus.IN_PROGRESS)
        assertThat(survey.startedAt).isNotNull()
    }

    @Test
    fun `IN_PROGRESS 상태일 때 end()를 호출하면 END로 전이된다`() {
        // given
        val survey = Survey.of("title", "desc", now, now.plusDays(1))
        survey.start()

        // when
        survey.end()

        assertThat(survey.status).isEqualTo(SurveyStatus.END)
        assertThat(survey.endedAt).isNotNull()
    }

    @Test
    fun `END 상태에서 다시 start()를 호출하면 예외가 발생한다`() {
        // given
        val survey = Survey.of("title", "desc", now, now.plusDays(1))
        survey.start()
        survey.end()

        // when & then
        val exception = assertThrows<IllegalStateException> {
            survey.start()
        }

        assertThat(exception.message).isEqualTo("Cannot transition from END to IN_PROGRESS")
    }

    @Test
    fun `READY 상태에서 바로 end()를 호출하면 예외가 발생한다`() {
        // given
        val survey = Survey.of("title", "desc", now, now.plusDays(1))

        // when & then
        val exception = assertThrows<IllegalStateException> {
            survey.end()
        }

        assertThat(exception.message).isEqualTo("Cannot transition from READY to END")
    }


}
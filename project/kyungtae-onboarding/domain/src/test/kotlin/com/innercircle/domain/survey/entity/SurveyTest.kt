package com.innercircle.domain.survey.entity

import com.innercircle.domain.survey.entity.SurveyTestFixtures.survey
import com.innercircle.domain.survey.entity.SurveyTestFixtures.surveyCreateCommand
import com.innercircle.survey.entity.Survey
import com.innercircle.survey.entity.SurveyStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class SurveyTest {

    private val surveyName = "설문 이름"

    private val surveyDescription = "설문 설명"

    private val now = LocalDateTime.now()

    private val tomorrow = now.plusDays(1)

    @Test
    fun `Survey는 of 메서드로 생성되고 기본 상태는 READY이다`() {
        // given
        val survey = survey()

        // when & then
        assertThat(survey.context.name).isEqualTo(surveyName)
        assertThat(survey.context.description).isEqualTo(surveyDescription)
        assertThat(survey.status).isEqualTo(SurveyStatus.READY)
    }

    @Test
    fun `startAt이 endAt보다 늦으면 예외가 발생한다`() {
        // when & then
        val exception = assertThrows<IllegalArgumentException> {
            surveyCreateCommand(
                surveyName = surveyName,
                surveyDescription = surveyDescription,
                startAt = tomorrow,
                endAt = now
            ).let { Survey.from(it) }
        }

        assertThat(exception.message).isEqualTo("startAt must be before endAt")
    }

    @Test
    fun `READY 상태일 때 start()를 호출하면 IN_PROGRESS로 전이된다`() {
        // given
        val survey = survey()

        // when
        survey.start()

        // then
        assertThat(survey.status).isEqualTo(SurveyStatus.IN_PROGRESS)
        assertThat(survey.startedAt).isNotNull()
    }

    @Test
    fun `IN_PROGRESS 상태일 때 end()를 호출하면 END로 전이된다`() {
        // given
        val survey = survey()
        survey.start()

        // when
        survey.end()

        assertThat(survey.status).isEqualTo(SurveyStatus.END)
        assertThat(survey.endedAt).isNotNull()
    }

    @Test
    fun `END 상태에서 다시 start()를 호출하면 예외가 발생한다`() {
        // given
        val survey = survey()
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
        val survey = survey()

        // when & then
        val exception = assertThrows<IllegalStateException> {
            survey.end()
        }

        assertThat(exception.message).isEqualTo("Cannot transition from READY to END")
    }
}
package com.example.doohyeononboarding.api.service

import com.example.doohyeononboarding.api.service.request.CreateSurveyRequest
import com.example.doohyeononboarding.api.service.request.QuestionRequest
import com.example.doohyeononboarding.domain.question.QuestionType
import com.example.doohyeononboarding.domain.servey.Survey
import com.example.doohyeononboarding.domain.servey.SurveyRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SurveyServiceTest @Autowired constructor(
    private val surveyService: SurveyService,
    private val surveyRepository: SurveyRepository,
) {

    @AfterEach
    fun tearDown() {
        surveyRepository.deleteAll()
    }

    @DisplayName("설문 항목이 0개인 경우 예외가 발생한다.")
    @Test
    fun createSurvey_Fail1() {
        // given
        val request = CreateSurveyRequest(
            title = "설문조사 제목",
            description = "설문조사 설명",
            questions = emptyList()
        )

        // when // then
        assertThatThrownBy { surveyService.createSurvey(request) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("설문 항목은 1개 이상 10개 이하이어야 합니다.")
    }

    @DisplayName("설문 항목이 11개인 경우 예외가 발생한다.")
    @Test
    fun createSurvey_Fail2() {
        // given
        val request = CreateSurveyRequest(
            title = "설문조사 제목",
            description = "설문조사 설명",
            questions = (1..11).map {
                QuestionRequest(
                    title = "질문 $it",
                    description = "설명 $it",
                    type = QuestionType.SHORT_TEXT,
                    isRequired = true,
                    options = null
                )
            }
        )

        // when // then
        assertThatThrownBy { surveyService.createSurvey(request) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("설문 항목은 1개 이상 10개 이하이어야 합니다.")
    }

    @DisplayName("설문 조사를 정상적으로 생성된다.")
    @Test
    fun createSurvey_Success() {
        // given
        val request = CreateSurveyRequest(
            title = "설문조사 제목",
            description = "설문조사 설명",
            questions = listOf(
                QuestionRequest(
                    title = "이름",
                    description = "이름을 입력하세요",
                    type = QuestionType.SHORT_TEXT,
                    isRequired = true,
                    options = null
                )
            )
        )
        surveyRepository.save(
            Survey(
                title = request.title,
                description = request.description,
            )
        )

        // when
        val response = surveyService.createSurvey(request)

        // then
        assertThat(response.surveyId).isNotNull()
    }

}

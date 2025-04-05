package org.survey.taehwanonboarding.application.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.survey.taehwanonboarding.api.dto.QuestionRequest
import org.survey.taehwanonboarding.api.dto.QuestionType
import org.survey.taehwanonboarding.api.dto.SurveyCreateRequest
import org.survey.taehwanonboarding.domain.entity.survey.Survey
import org.survey.taehwanonboarding.domain.repository.survey.SurveyRepository
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class SurveyServiceTest {
    @Mock
    private lateinit var surveyRepository: SurveyRepository

    @InjectMocks
    private lateinit var surveyService: SurveyService

    private lateinit var validRequest: SurveyCreateRequest

    @BeforeEach
    fun setup() {
        validRequest =
            SurveyCreateRequest(
                title = "클라이밍장 선호도 조사",
                description = "MVP 타겟팅 조사",
                questions =
                listOf(
                    QuestionRequest(
                        title = "선호하는 암장을 선택해주세요",
                        description = "ex.) 더클라임/서울숲/피커스",
                        required = true,
                        orderNumber = 1,
                        type = QuestionType.SHORT_ANSWER,
                        maxLength = 50,
                    ),
                    QuestionRequest(
                        title = "자주 이용하는 지점을 선택해주세요",
                        description = "하나만 선택해주세요",
                        required = true,
                        orderNumber = 2,
                        type = QuestionType.SINGLE_SELECTION,
                        options = listOf("판교", "강남", "사당"),
                    ),
                ),
            )
    }

    @Test
    fun `올바른 데이터를 입력 시 생성된 엔티티를 반환한다`() {
        // given
        val savedSurvey =
            Survey(
                id = 1L,
                title = validRequest.title,
                description = validRequest.description,
                status = Survey.SurveyStatus.DRAFT,
                createdAt = LocalDateTime.now(),
            )

        // when
        `when`(surveyRepository.save(any(Survey::class.java))).thenReturn(savedSurvey)
        val response = surveyService.createSurvey(validRequest)

        // then
        assertEquals(savedSurvey.id, response.id)
        assertEquals(savedSurvey.title, response.title)
        assertEquals(savedSurvey.status.name, response.status)
        verify(surveyRepository, times(1)).save(any(Survey::class.java))
    }

    @Test
    fun `설문 제목은 필수값이다`() {
        // given
        val invalidRequest = validRequest.copy(title = "")

        // when & then
        val exception =
            assertThrows(IllegalArgumentException::class.java) {
                surveyService.createSurvey(invalidRequest)
            }

        assertTrue(exception.message?.contains(ValidationMessage.IS_TITLE_REQUIRED.toString()) ?: false)
        verify(surveyRepository, never()).save(any(Survey::class.java))
    }

    @Test
    fun `질문 항목은 필수값이다`() {
        // given
        val invalidRequest = validRequest.copy(questions = emptyList())

        // when & then
        val exception =
            assertThrows(IllegalArgumentException::class.java) {
                surveyService.createSurvey(invalidRequest)
            }

        assertTrue(exception.message?.contains(ValidationMessage.IS_REQUIRED_MIN_LENGTH_TEN.toString()) ?: false)
        verify(surveyRepository, never()).save(any(Survey::class.java))
    }

    @Test
    fun `질문은 10개를 초과할 수 없다`() {
        // given
        val manyQuestions =
            (1..11).map {
                QuestionRequest(
                    title = "질문 $it",
                    type = QuestionType.SHORT_ANSWER,
                    required = false,
                    orderNumber = it,
                )
            }
        val invalidRequest = validRequest.copy(questions = manyQuestions)

        // when & then
        val exception =
            assertThrows(IllegalArgumentException::class.java) {
                surveyService.createSurvey(invalidRequest)
            }

        assertTrue(exception.message?.contains(ValidationMessage.IS_REQUIRED_MIN_LENGTH_TEN.toString()) ?: false)
        verify(surveyRepository, never()).save(any(Survey::class.java))
    }

    @Test
    fun `선택형 질문은 옵션 항목이 필수이다`() {
        // given
        val invalidQuestion =
            QuestionRequest(
                title = "선택 질문",
                type = QuestionType.SINGLE_SELECTION,
                required = true,
                orderNumber = 1,
                options = emptyList(),
            )
        val invalidRequest =
            validRequest.copy(
                questions = listOf(invalidQuestion),
            )

        // when & then
        val exception =
            assertThrows(IllegalArgumentException::class.java) {
                surveyService.createSurvey(invalidRequest)
            }

        assertTrue(exception.message?.contains(ValidationMessage.IS_MIN_OPTION_COUNT_ONE.toString()) ?: false)
        verify(surveyRepository, never()).save(any(Survey::class.java))
    }
}
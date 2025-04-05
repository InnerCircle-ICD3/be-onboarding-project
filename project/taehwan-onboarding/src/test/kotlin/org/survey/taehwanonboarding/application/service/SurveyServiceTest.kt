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
import org.survey.taehwanonboarding.domain.entity.survey.*
import org.survey.taehwanonboarding.domain.entity.survey.Survey
import org.survey.taehwanonboarding.domain.repository.survey.SurveyRepository
import java.time.LocalDateTime
import java.util.Optional

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

@ExtendWith(MockitoExtension::class)
class SurveyServiceGetTest {

    @Test
    fun `ID로 설문조사를 찾으면 상세 정보를 반환한다`() {
        // given
        val surveyRepository = mock(SurveyRepository::class.java)
        val surveyService = SurveyService(surveyRepository)

        val surveyId = 1L
        val survey = Survey(
            id = surveyId,
            title = "클라이밍장 선호도 조사",
            description = "MVP 타겟팅 조사",
            status = Survey.SurveyStatus.ACTIVE,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        )

        // 질문 항목 추가
        val shortAnswerItem = ShortAnswerItem(
            id = 1L,
            title = "선호하는 암장을 선택해주세요",
            description = "ex.) 더클라임/서울숲/피커스",
            required = true,
            orderNumber = 1,
            maxLength = 50,
        )
        survey.addItem(shortAnswerItem)

        val singleSelectionItem = SingleSelectionItem(
            id = 2L,
            title = "자주 이용하는 지점을 선택해주세요",
            description = "하나만 선택해주세요",
            required = true,
            orderNumber = 2,
            options = mutableListOf("판교", "강남", "사당"),
        )
        survey.addItem(singleSelectionItem)

        `when`(surveyRepository.findById(surveyId)).thenReturn(Optional.of(survey))

        // when
        val response = surveyService.getSurveyById(surveyId)

        // then
        assertEquals(surveyId, response.id)
        assertEquals(survey.title, response.title)
        assertEquals(survey.description, response.description)
        assertEquals(survey.status.name, response.status)

        // 질문 항목 검증
        assertEquals(2, response.items.size)

        // 첫 번째 항목 검증 (단답형)
        assertEquals(1L, response.items[0].id)
        assertEquals("선호하는 암장을 선택해주세요", response.items[0].title)
        assertEquals(QuestionType.SHORT_ANSWER, response.items[0].type)
        assertEquals(50, response.items[0].maxLength)

        // 두 번째 항목 검증 (단일 선택)
        assertEquals(2L, response.items[1].id)
        assertEquals("자주 이용하는 지점을 선택해주세요", response.items[1].title)
        assertEquals(QuestionType.SINGLE_SELECTION, response.items[1].type)
        assertEquals(3, response.items[1].options?.size)
        assertEquals("판교", response.items[1].options?.get(0))

        // 메서드 호출 검증
        verify(surveyRepository, times(1)).findById(surveyId)
    }

    @Test
    fun `존재하지 않는 설문조사 ID로 조회하면 예외가 발생한다`() {
        // given
        val surveyRepository = mock(SurveyRepository::class.java)
        val surveyService = SurveyService(surveyRepository)

        val nonExistentId = 999L
        `when`(surveyRepository.findById(nonExistentId)).thenReturn(Optional.empty())

        // when & then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyService.getSurveyById(nonExistentId)
        }

        assertTrue(exception.message?.contains("존재하지 않는 설문조사입니다") ?: false)
        verify(surveyRepository, times(1)).findById(nonExistentId)
    }

    @Test
    fun `삭제된 설문조사 ID로 조회하면 예외가 발생한다`() {
        // given
        val surveyRepository = mock(SurveyRepository::class.java)
        val surveyService = SurveyService(surveyRepository)

        val deletedSurveyId = 2L
        val deletedSurvey = Survey(
            id = deletedSurveyId,
            title = "삭제된 설문조사",
            status = Survey.SurveyStatus.ARCHIVED,
        )

        `when`(surveyRepository.findById(deletedSurveyId)).thenReturn(Optional.of(deletedSurvey))

        // when & then
        val exception = assertThrows(IllegalArgumentException::class.java) {
            surveyService.getSurveyById(deletedSurveyId)
        }

        assertTrue(exception.message?.contains("삭제된 설문조사입니다") ?: false)
        verify(surveyRepository, times(1)).findById(deletedSurveyId)
    }

    @Test
    fun `설문조사 목록을 조회하면 상태가 ARCHIVED가 아닌 모든 설문조사를 반환한다`() {
        // given
        val surveyRepository = mock(SurveyRepository::class.java)
        val surveyService = SurveyService(surveyRepository)

        val surveys = listOf(
            Survey(
                id = 1L,
                title = "활성 설문조사",
                description = "첫 번째 설문",
                status = Survey.SurveyStatus.ACTIVE,
                createdAt = LocalDateTime.now(),
            ),
            Survey(
                id = 2L,
                title = "임시저장 설문조사",
                description = "두 번째 설문",
                status = Survey.SurveyStatus.DRAFT,
                createdAt = LocalDateTime.now(),
            ),
            Survey(
                id = 3L,
                title = "종료된 설문조사",
                description = "세 번째 설문",
                status = Survey.SurveyStatus.CLOSED,
                createdAt = LocalDateTime.now(),
            ),
        )

        `when`(surveyRepository.findAllByStatusNot(Survey.SurveyStatus.ARCHIVED)).thenReturn(surveys)

        // when
        val response = surveyService.getSurveys()

        // then
        assertEquals(3, response.size)
        assertEquals("활성 설문조사", response[0].title)
        assertEquals("임시저장 설문조사", response[1].title)
        assertEquals("종료된 설문조사", response[2].title)

        verify(surveyRepository, times(1)).findAllByStatusNot(Survey.SurveyStatus.ARCHIVED)
    }

    @Test
    fun `상태 필터로 설문조사를 조회하면 해당 상태의 설문조사만 반환한다`() {
        // given
        val surveyRepository = mock(SurveyRepository::class.java)
        val surveyService = SurveyService(surveyRepository)

        val activeSurveys = listOf(
            Survey(
                id = 1L,
                title = "활성 설문조사 1",
                description = "첫 번째 활성 설문",
                status = Survey.SurveyStatus.ACTIVE,
                createdAt = LocalDateTime.now(),
            ),
            Survey(
                id = 4L,
                title = "활성 설문조사 2",
                description = "두 번째 활성 설문",
                status = Survey.SurveyStatus.ACTIVE,
                createdAt = LocalDateTime.now(),
            ),
        )

        `when`(surveyRepository.findAllByStatus(Survey.SurveyStatus.ACTIVE)).thenReturn(activeSurveys)

        // when
        val response = surveyService.getSurveys(Survey.SurveyStatus.ACTIVE)

        // then
        assertEquals(2, response.size)
        assertEquals("활성 설문조사 1", response[0].title)
        assertEquals("활성 설문조사 2", response[1].title)
        assertEquals(Survey.SurveyStatus.ACTIVE.name, response[0].status)

        verify(surveyRepository, times(1)).findAllByStatus(Survey.SurveyStatus.ACTIVE)
    }
}
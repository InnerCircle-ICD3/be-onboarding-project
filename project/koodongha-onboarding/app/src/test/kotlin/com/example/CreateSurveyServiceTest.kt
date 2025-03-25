package com.example

import com.example.dto.SurveyItemRequest
import com.example.dto.SurveyRequest
import com.example.entity.InputType
import com.example.service.CreateSurveyService
import com.example.repository.SurveyRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.any

class CreateSurveyServiceTest {

    private val surveyRepository = mock<SurveyRepository>()
    private val createSurveyService = CreateSurveyService(surveyRepository)

    @Test
    fun `설문 항목이 1개 이상 10개 이하일 경우 생성 성공`() {
        val request = SurveyRequest(
            title = "타이틀",
            description = "설명",
            items = (1..10).map {
                SurveyItemRequest(
                    name = "질문$it",
                    description = "설명$it",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true,
                    options = null
                )
            }
        )

        assertDoesNotThrow { createSurveyService.createSurvey(request) }
        verify(surveyRepository).save(any())
    }

    @Test
    fun `11개 초과 설문 항목일 경우 예외 발생`() {
        val request = SurveyRequest(
            title = "타이틀",
            description = "설명",
            items = (1..11).map {
                SurveyItemRequest(
                    name = "질문$it",
                    description = "설명$it",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true,
                    options = null
                )
            }
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("설문 항목은 1개 이상 10개 이하만 가능합니다.", exception.message)
    }

    @Test
    fun `선택형인데 옵션이 null이면 예외 발생`() {
        val request = SurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                SurveyItemRequest(
                    name = "언어 선택",
                    description = "옵션 없음",
                    inputType = InputType.SINGLE_CHOICE,
                    isRequired = true,
                    options = null
                )
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("선택형 항목에는 옵션이 필수입니다.", exception.message)
    }

    @Test
    fun `선택형인데 옵션이 빈 리스트면 예외 발생`() {
        val request = SurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                SurveyItemRequest(
                    name = "언어 선택",
                    description = "빈 옵션 리스트",
                    inputType = InputType.MULTI_CHOICE,
                    isRequired = true,
                    options = emptyList()
                )
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("선택형 항목에는 옵션이 필수입니다.", exception.message)
    }

    @Test
    fun `항목 이름이 비어있을 경우 예외 발생`() {
        val request = SurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                SurveyItemRequest(
                    name = "",
                    description = "설명",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true,
                    options = null
                )
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("항목 이름은 필수입니다.", exception.message)
    }

    @Test
    fun `항목 설명이 비어있을 경우 예외 발생`() {
        val request = SurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                SurveyItemRequest(
                    name = "질문 1",
                    description = "",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true,
                    options = null
                )
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("항목 설명은 필수입니다.", exception.message)
    }

    @Test
    fun `설문 제목이 비어있을 경우 예외 발생`() {
        val request = SurveyRequest(
            title = "",
            description = "설명",
            items = listOf(
                SurveyItemRequest(
                    name = "질문 1",
                    description = "설명",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true,
                    options = null
                )
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("설문 제목은 필수입니다.", exception.message)
    }

    @Test
    fun `설문 설명이 비어있을 경우 예외 발생`() {
        val request = SurveyRequest(
            title = "제목",
            description = "",
            items = listOf(
                SurveyItemRequest(
                    name = "질문 1",
                    description = "설명",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true,
                    options = null
                )
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("설문 설명은 필수입니다.", exception.message)
    }
} 

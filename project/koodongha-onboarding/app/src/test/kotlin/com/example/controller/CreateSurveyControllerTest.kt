package com.example.service

import com.example.dto.CreateSurveyItemRequest
import com.example.dto.CreateSurveyRequest
import com.example.entity.InputType
import com.example.repository.SurveyRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class CreateSurveyServiceTest {

    private val surveyRepository = mock<SurveyRepository>()
    private val createSurveyService = CreateSurveyService(surveyRepository)

    @Test
    fun `설문 항목이 1개 이상 10개 이하일 경우 생성 성공`() {
        val request = CreateSurveyRequest(
            title = "타이틀",
            description = "설명",
            items = (1..10).map {
                CreateSurveyItemRequest(
                    name = "질문$it",
                    description = "설명$it",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true
                )
            }
        )

        assertDoesNotThrow { createSurveyService.createSurvey(request) }
    }

    @Test
    fun `11개 초과 설문 항목일 경우 예외 발생`() {
        val request = CreateSurveyRequest(
            title = "타이틀",
            description = "설명",
            items = (1..11).map {
                CreateSurveyItemRequest(
                    name = "질문$it",
                    description = "설명$it",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true
                )
            }
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("설문 항목은 1개 이상 10개 이하만 가능합니다.", exception.message)
    }

    @Test
    fun `선택형인데 옵션 누락 시 예외 발생`() {
        val request = CreateSurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "옵션이 없어요",
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
    fun `항목 이름이 누락된 경우 예외 발생`() {
        val request = CreateSurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "",
                    description = "설명",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true
                )
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("항목 이름은 필수입니다.", exception.message)
    }

    @Test
    fun `항목 설명이 누락된 경우 예외 발생`() {
        val request = CreateSurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "질문 1",
                    description = "",
                    inputType = InputType.SHORT_TEXT,
                    isRequired = true
                )
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            createSurveyService.createSurvey(request)
        }

        assertEquals("항목 설명은 필수입니다.", exception.message)
    }

    @Test
    fun `단일 선택 항목에 옵션 누락시 예외 발생`() {
        val request = CreateSurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "언어를 고르세요",
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
    fun `다중 선택 항목에 옵션 누락시 예외 발생`() {
        val request = CreateSurveyRequest(
            title = "타이틀",
            description = "설명",
            items = listOf(
                CreateSurveyItemRequest(
                    name = "언어 선택",
                    description = "언어를 고르세요",
                    inputType = InputType.MULTI_CHOICE,
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
}

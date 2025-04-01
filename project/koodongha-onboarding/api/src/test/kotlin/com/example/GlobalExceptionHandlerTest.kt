package com.example

import com.example.exception.*
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()
    private val mockRequest: HttpServletRequest = mock {
        on { requestURI } doReturn "/test-uri"
    }

    @Test
    @DisplayName("Should handle BusinessException")
    fun handleBusinessExceptionTest() {
        val exception = SurveyNotFoundException()
        val response = handler.handleBusinessException(exception, mockRequest)
        assertEquals(ErrorCode.SURVEY_NOT_FOUND.status, response.statusCode.value())
        assertEquals("설문을 찾을 수 없습니다.", response.body?.message)
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException")
    fun handleIllegalArgumentTest() {
        val response = handler.handleIllegalArgument(IllegalArgumentException("잘못된 파라미터"), mockRequest)
        assertEquals(400, response.statusCode.value())
        assertEquals("잘못된 파라미터", response.body?.message)
    }

    @Test
    @DisplayName("Should handle MethodArgumentTypeMismatchException")
    fun handleTypeMismatchTest() {
        val e = MethodArgumentTypeMismatchException("123", String::class.java, "surveyId", null, null)
        val response = handler.handleTypeMismatch(e, mockRequest)
        assertEquals(400, response.statusCode.value())
        assertTrue(response.body?.message?.contains("surveyId") ?: false)
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with field errors")
    fun handleValidationExceptionTest() {
        val bindingResult = mock<BindingResult> {
            on { fieldErrors } doReturn listOf(
                FieldError("dto", "title", "must not be blank"),
                FieldError("dto", "items", "must not be empty")
            )
        }
        val e = MethodArgumentNotValidException(null, bindingResult)
        val response = handler.handleValidationException(e, mockRequest)

        assertEquals(400, response.statusCode.value())
        assertTrue(response.body?.message?.contains("title") ?: false)
        assertTrue(response.body?.message?.contains("items") ?: false)
    }

    @Test
    @DisplayName("Should handle unknown Exception")
    fun handleGenericExceptionTest() {
        val e = RuntimeException("Something went wrong")
        val response = handler.handleGenericException(e, mockRequest)
        assertEquals(500, response.statusCode.value())
        assertTrue(response.body?.message?.contains("Something went wrong") ?: false)
    }
}

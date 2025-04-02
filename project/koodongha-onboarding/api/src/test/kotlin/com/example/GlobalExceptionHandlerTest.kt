package com.example

import com.example.exception.*
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.core.MethodParameter
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()
    private val mockRequest: HttpServletRequest = mock {
        on { requestURI } doReturn "/test-uri"
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException")
    fun handleValidationExceptionTest() {
        val bindingResult = mock<BindingResult> {
            on { fieldErrors } doReturn listOf(
                FieldError("target", "title", "제목은 필수입니다."),
                FieldError("target", "items", "항목은 하나 이상 있어야 합니다.")
            )
        }
    
        val exception = MethodArgumentNotValidException(mock(), bindingResult)
        val response = handler.handleValidationException(exception, mockRequest)
    
        assertEquals(400, response.statusCode.value())
        assertTrue(response.body?.message?.contains("제목은 필수입니다.") ?: false)
        assertTrue(response.body?.message?.contains("항목은 하나 이상 있어야 합니다.") ?: false)
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
        val mockParam = mock<MethodParameter>()
        val e = MethodArgumentTypeMismatchException("123", String::class.java, "surveyId", mockParam, null)
        val response = handler.handleTypeMismatch(e, mockRequest)
        assertEquals(400, response.statusCode.value())
        assertTrue(response.body?.message?.contains("surveyId") ?: false)
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

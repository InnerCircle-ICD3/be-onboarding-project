// src/main/kotlin/com/innercircle/survey/controller/ResponseController.kt
package com.innercircle.survey.controller

import com.innercircle.survey.dto.*
import com.innercircle.survey.service.ResponseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/surveys/{surveyId}/responses")
class ResponseController(
    private val responseService: ResponseService
) {
    // 설문응답 제출 API
    @PostMapping
    fun submitResponse(
        @PathVariable surveyId: Long,
        @Valid @RequestBody request: SubmitResponseRequest
    ): ResponseEntity<ApiResponse<ResponseResponse>> {
        val responseResponse = responseService.submitResponse(surveyId, request)
        return ResponseEntity(
            ApiResponse(
                success = true,
                data = responseResponse
            ),
            HttpStatus.CREATED
        )
    }
    
    // 설문응답 조회 API
    @GetMapping
    fun getSurveyResponses(@PathVariable surveyId: Long): ResponseEntity<ApiResponse<List<ResponseResponse>>> {
        val responses = responseService.getSurveyResponses(surveyId)
        return ResponseEntity(
            ApiResponse(
                success = true,
                data = responses
            ),
            HttpStatus.OK
        )
    }
    
    // 설문응답 검색 API (Advanced)
    @GetMapping("/search")
    fun searchResponses(
        @PathVariable surveyId: Long,
        @ModelAttribute request: SearchResponseRequest
    ): ResponseEntity<ApiResponse<List<ResponseResponse>>> {
        val responses = responseService.searchResponses(surveyId, request)
        return ResponseEntity(
            ApiResponse(
                success = true,
                data = responses
            ),
            HttpStatus.OK
        )
    }
}
// src/main/kotlin/com/innercircle/survey/controller/SurveyController.kt
package com.innercircle.survey.controller

import com.innercircle.survey.dto.*
import com.innercircle.survey.service.SurveyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/surveys")
class SurveyController(
    private val surveyService: SurveyService
) {
    // 설문조사 생성 API
    @PostMapping
    fun createSurvey(@Valid @RequestBody request: CreateSurveyRequest): ResponseEntity<ApiResponse<SurveyResponse>> {
        val surveyResponse = surveyService.createSurvey(request)
        return ResponseEntity(
            ApiResponse(
                success = true,
                data = surveyResponse
            ),
            HttpStatus.CREATED
        )
    }
    
    // 설문조사 조회 API
    @GetMapping("/{surveyId}")
    fun getSurvey(@PathVariable surveyId: Long): ResponseEntity<ApiResponse<SurveyResponse>> {
        val surveyResponse = surveyService.getSurvey(surveyId)
        return ResponseEntity(
            ApiResponse(
                success = true,
                data = surveyResponse
            ),
            HttpStatus.OK
        )
    }
    
    // 설문조사 수정 API
    @PutMapping("/{surveyId}")
    fun updateSurvey(
        @PathVariable surveyId: Long,
        @Valid @RequestBody request: UpdateSurveyRequest
    ): ResponseEntity<ApiResponse<SurveyResponse>> {
        val surveyResponse = surveyService.updateSurvey(surveyId, request)
        return ResponseEntity(
            ApiResponse(
                success = true,
                data = surveyResponse
            ),
            HttpStatus.OK
        )
    }
}
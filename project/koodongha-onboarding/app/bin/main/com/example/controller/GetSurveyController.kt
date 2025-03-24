package com.example.controller

import com.example.dto.SurveyDetailResponse
import com.example.service.GetSurveyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/surveys")
class GetSurveyController(
    private val getSurveyService: GetSurveyService
) {
    @GetMapping("/{id}")
    fun getSurvey(@PathVariable id: Long): ResponseEntity<SurveyDetailResponse> {
        val response = getSurveyService.getSurvey(id)
        return ResponseEntity.ok(response)
    }
}

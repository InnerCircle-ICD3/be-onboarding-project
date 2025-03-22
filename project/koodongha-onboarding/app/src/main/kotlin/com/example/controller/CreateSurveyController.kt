package com.example.controller

import com.example.dto.CreateSurveyRequest
import com.example.service.CreateSurveyService
import com.springframework.http.ResponseEntity
import com.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/surveys")
class CreateSurveyController(
    private val createSurveyService: CreateSurveyService
) {
    @PostMapping
    fun createSurvey(@RequestBody request: CreateSurveyRequest): ResponseEntity<Long> {
        val id = createSurveyService.createSurvey(request)
        return ResponseEntity.ok(id)
    }
}

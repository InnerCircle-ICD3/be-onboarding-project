package com.example.api.controller

import com.example.dto.CreateSurveyRequest
import com.example.service.CreateSurveyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/surveys")
class CreateSurveyController(
    private val createSurveyService: CreateSurveyService
) {

    @PostMapping
    fun createSurvey(@RequestBody request: CreateSurveyRequest): ResponseEntity<Void> {
        createSurveyService.createSurvey(request)
        return ResponseEntity.ok().build()
    }
}

package com.example.controller

import com.example.dto.CreateSurveyRequest
import com.example.service.CreateSurveyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/surveys")
class CreateSurveyController(
    private val createSurveyService: CreateSurveyService
) {

    @PostMapping
    fun createSurvey(@RequestBody @Valid request: CreateSurveyRequest): ResponseEntity<Void> {
        createSurveyService.createSurvey(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}

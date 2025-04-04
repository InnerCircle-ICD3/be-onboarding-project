package com.onboarding.form.controller

import CreateAnswerRequestDto
import com.onboarding.form.request.CreateSurveyDto
import com.onboarding.form.response.SurveyDto
import com.onboarding.form.service.SurveyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SurveyController(
    private val service: SurveyService,
) {
    @PostMapping("/surveys")
    fun create(@RequestBody surveyDto: CreateSurveyDto): ResponseEntity<SurveyDto> {
        val survey = service.createSurvey(surveyDto)

        return ResponseEntity.ok(SurveyDto.of(survey))
    }

    @PutMapping("/surveys/{id}")
    fun update(@RequestBody surveyDto: CreateSurveyDto, @PathVariable id: Long): ResponseEntity<SurveyDto> {
        val survey = service.updateSurvey(id, surveyDto)
        return ResponseEntity.ok(SurveyDto.of(survey))
    }

    @PutMapping("/surveys/{id}/submit")
    fun submitAnswers(
        @RequestBody createAnswerRequestDto: CreateAnswerRequestDto,
        @PathVariable id: Long
    ): ResponseEntity<Nothing> {
        service.submitAnswer(surveyId = id, createAnswerRequestDto)
        return ResponseEntity.ok(null)
    }
}


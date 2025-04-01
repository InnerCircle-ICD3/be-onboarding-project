package com.onboarding.form.controller

import com.onboarding.form.request.CreateSurveyDto
import com.onboarding.form.response.QuestionDto
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

        return ResponseEntity.ok(
            SurveyDto(
                id = survey.id!!,
                title = survey.title,
                description = survey.description,
                item = survey.getQuestions().map {
                    QuestionDto.of(it)
                }.toList()
            )
        )
    }

    @PutMapping("/surveys/{id}")
    fun update(@RequestBody surveyDto: CreateSurveyDto, @PathVariable id: Long): ResponseEntity<SurveyDto> {
        val survey = service.updateSurveyDto(id, surveyDto)

        return ResponseEntity.ok(
            SurveyDto(
                id = survey.id!!,
                title = survey.title,
                description = survey.description,
                item = survey.getQuestions().map {
                    QuestionDto.of(it)
                }.toList()
            )
        )
    }
}


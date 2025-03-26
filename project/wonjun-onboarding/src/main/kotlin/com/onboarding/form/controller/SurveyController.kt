package com.onboarding.form.controller

import com.onboarding.form.request.CreateSurveyDto
import com.onboarding.form.response.ItemDto
import com.onboarding.form.response.SurveyDto
import com.onboarding.form.service.SurveyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
                item = survey.item.map {
                    ItemDto(
                        title = it.title,
                        description = it.description,
                        type = it.type,
                        isRequired = it.isRequired,
                        options = it.options
                    )
                }.toList()
            )
        )
    }
}


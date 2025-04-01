package com.example.api.controller

import com.example.dto.SurveyUpdateRequest
import com.example.service.UpdateSurveyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/surveys")
class UpdateSurveyController(
    private val updateSurveyService: UpdateSurveyService
) {

    @PutMapping("/{id}")
    fun updateSurvey(
        @PathVariable id: Long,
        @RequestBody request: SurveyUpdateRequest
    ): ResponseEntity<Void> {
        updateSurveyService.updateSurvey(id, request)
        return ResponseEntity.ok().build()
    }
}

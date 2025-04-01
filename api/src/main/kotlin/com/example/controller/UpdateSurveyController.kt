package com.example.api.controller

import com.example.dto.AnswerSubmitDto
import com.example.service.UpdateSurveyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/surveys")
class UpdateSurveyController(
    private val updateSurveyService: UpdateSurveyService
) {

    @PutMapping("/{id}/answers")
    fun updateAnswer(
        @PathVariable id: Long,
        @RequestBody request: AnswerSubmitDto
    ): ResponseEntity<Void> {
        updateSurveyService.submitAnswer(id, request)
        return ResponseEntity.ok().build()
    }
}

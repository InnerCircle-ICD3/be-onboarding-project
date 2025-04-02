package com.example.controller

import com.example.dto.AnswerSubmitDto
import com.example.service.SurveyAnswerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/surveys")
class SurveyAnswerController(
    private val surveyAnswerService: SurveyAnswerService
) {

    @PostMapping("/{id}/answers")
    fun submitAnswer(
        @PathVariable id: Long,
        @RequestBody request: AnswerSubmitDto
    ): ResponseEntity<Void> {
        surveyAnswerService.submitAnswer(id, request)
        return ResponseEntity.ok().build()
    }
}

package com.example.controller

import com.example.dto.SurveyResponse
import com.example.service.GetSurveyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/surveys")
class GetSurveyController(
    private val getSurveyService: GetSurveyService
) {

    @GetMapping("/{id}")
    fun getSurvey(
        @PathVariable id: Long,
        @RequestParam(required = false) filterName: String?,
        @RequestParam(required = false) filterAnswer: String?
    ): ResponseEntity<SurveyResponse> {
        val response = getSurveyService.getSurvey(id, filterName, filterAnswer)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
    
}

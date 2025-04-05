package com.chosseang.seonghunonboarding.controller

import com.chosseang.seonghunonboarding.dto.AnswerResponse
import com.chosseang.seonghunonboarding.dto.AnswerSubmitRequest
import com.chosseang.seonghunonboarding.dto.ApiResponse
import com.chosseang.seonghunonboarding.service.AnswerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/answer")
class AnswerController(private val answerService: AnswerService) {

    @PostMapping("/submit")
    @ResponseBody
    fun submitAnswer(@RequestBody request: AnswerSubmitRequest): ResponseEntity<ApiResponse<AnswerResponse>> {
        val result = answerService.submitAnswer(request)

        val apiResponse = ApiResponse(
            status = HttpStatus.OK.value(),
            result = result
        )

        return ResponseEntity.ok(apiResponse)
    }
}

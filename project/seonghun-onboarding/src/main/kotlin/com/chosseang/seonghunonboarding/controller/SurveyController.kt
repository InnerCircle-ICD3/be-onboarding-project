package com.chosseang.seonghunonboarding.controller

import com.chosseang.seonghunonboarding.dto.ApiResponse
import com.chosseang.seonghunonboarding.dto.SurveyCreateRequest
import com.chosseang.seonghunonboarding.dto.SurveyCreateResponse
import com.chosseang.seonghunonboarding.entity.Item
import com.chosseang.seonghunonboarding.entity.Survey
import com.chosseang.seonghunonboarding.service.SurveyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/surveys")
class SurveyController (val surveyService: SurveyService) {

    @GetMapping
    fun searchSurveys() : List<Survey> {

        return surveyService.searchSurvey();
    }

    @PostMapping("/create")
    @ResponseBody
    fun createSurvey(@RequestBody surveyCreateRequest: SurveyCreateRequest): ResponseEntity<ApiResponse<SurveyCreateResponse>> {

        val surveyResponse = SurveyCreateRequest(
            name = surveyCreateRequest.name,
            description = surveyCreateRequest.description,
            items = mutableListOf()
        ).apply {
            items = surveyCreateRequest.items.map {
                itemRequest ->
                Item(
                    id = null,
                    name = itemRequest.name,
                    description = itemRequest.description,
                    type = itemRequest.type,
                    contents = itemRequest.contents,
                    survey = Survey(
                        id = itemRequest.id,
                        name = itemRequest.name,
                        description = itemRequest.description,
                        items = surveyCreateRequest.items.toMutableList()
                    )
                )
            }.toMutableList()
        }.let(surveyService::createSurvey)

        val apiResponse = ApiResponse(
            status = HttpStatus.OK.value(),
            result = surveyResponse
        )

        return ResponseEntity.ok(apiResponse)
    }
}

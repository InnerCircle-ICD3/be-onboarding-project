package com.chosseang.seonghunonboarding.controller

import com.chosseang.seonghunonboarding.dto.SurveyCreateRequest
import com.chosseang.seonghunonboarding.dto.SurveyCreateResponse
import com.chosseang.seonghunonboarding.entity.Item
import com.chosseang.seonghunonboarding.entity.Survey
import com.chosseang.seonghunonboarding.service.SurveyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/surveys")
class SurveyController (val surveyService: SurveyService) {

    @PostMapping("/create")
    @ResponseBody
    fun createSurvey(@RequestBody surveyCreateRequest: SurveyCreateRequest): SurveyCreateResponse {

        return SurveyCreateRequest(
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
    }
}

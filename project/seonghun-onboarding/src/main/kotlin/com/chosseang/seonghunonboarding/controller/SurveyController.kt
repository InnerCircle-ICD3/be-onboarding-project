package com.chosseang.seonghunonboarding.controller

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
    fun createSurvey(@RequestBody survey: Survey): Survey {

        return Survey(
            name = survey.name,
            description = survey.description,
            items = mutableListOf()
        ).apply {
            items = survey.items.map {
                itemRequest ->
                Item(
                    id = null,
                    name = itemRequest.name,
                    description = itemRequest.description,
                    type = itemRequest.type,
                    contents = itemRequest.contents,
                    survey = this
                )
            }.toMutableList()
        }.let(surveyService::createSurvey)
    }
}

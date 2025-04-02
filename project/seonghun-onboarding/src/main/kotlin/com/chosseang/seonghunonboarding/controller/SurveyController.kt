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
        // DTO에서 엔티티로 변환 (양방향 관계 설정)
        val surveyValue = Survey(
            name = survey.name,
            description = survey.description,
            items = mutableListOf()
        )

        // Item 엔티티 생성 및 Survey와 연결
        val items = survey.items.map { itemRequest ->
            Item(
                id = null,
                name = itemRequest.name,
                description = itemRequest.description,
                type = itemRequest.type,
                contents = itemRequest.contents,
                survey = surveyValue  // 양방향 관계 설정
            )
        }

        // 양방향 관계 설정
        surveyValue.items = items.toMutableList()

        return surveyService.createSurvey(surveyValue)
    }
}

package com.chosseang.seonghunonboarding.controller

import com.chosseang.seonghunonboarding.entity.Item
import com.chosseang.seonghunonboarding.entity.Survey
import com.chosseang.seonghunonboarding.enum.ItemType
import com.chosseang.seonghunonboarding.service.SurveyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/surveys")
class SurveyController (val surveyService: SurveyService) {

    @PostMapping("/create")
    fun createSurvey(@RequestBody survey: Survey): Survey {
        // DTO에서 엔티티로 변환 (양방향 관계 설정)
        val survey = Survey(
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
                survey = survey  // 양방향 관계 설정
            )
        }

        // 양방향 관계 설정
        survey.items = items.toMutableList()

        return surveyService.createSurvey(survey)
    }
}

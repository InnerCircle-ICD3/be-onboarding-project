package com.chosseang.seonghunonboarding.service

import com.chosseang.seonghunonboarding.entity.Survey
import com.chosseang.seonghunonboarding.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SurveyService(val repository: SurveyRepository) {

    @Transactional
    fun createSurvey(survey: Survey): Survey {
        // 양방향 관계 설정을 위해 Survey와 Item 사이의 관계 설정
        val items = survey.items.toMutableList()
        survey.items = mutableListOf()  // 기존 리스트 비우기

        // 새로운 Survey 인스턴스 저장
        val savedSurvey = repository.save(survey)

        // 저장된 Survey에 Item 추가 (양방향 관계 설정)
        items.forEach { savedSurvey.addItem(it) }

        return repository.save(savedSurvey)
    }
}

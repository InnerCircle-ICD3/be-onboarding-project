package com.chosseang.seonghunonboarding.service

import com.chosseang.seonghunonboarding.entity.Survey
import com.chosseang.seonghunonboarding.repository.SurveyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SurveyService(val repository: SurveyRepository) {

    @Transactional
    fun createSurvey(survey: Survey): Survey {
        // 각 아이템의 survey 참조 설정 (양방향 관계 유지)
        survey.items.forEach { item ->
            item.survey = survey
        }

        return repository.save(survey)
    }
}

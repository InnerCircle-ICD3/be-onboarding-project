package com.innercircle.domain.survey.repository

import com.innercircle.survey.entity.SurveyAnswer
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyAnswerRepository : JpaRepository<SurveyAnswer, Long> {


}
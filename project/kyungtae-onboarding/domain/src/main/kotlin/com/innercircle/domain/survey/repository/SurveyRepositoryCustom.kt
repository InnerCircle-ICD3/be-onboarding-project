package com.innercircle.domain.survey.repository

fun interface SurveyWithFilterRepositoryCustom {
    fun enableDeletedFilter(showDeleted: Boolean)
}
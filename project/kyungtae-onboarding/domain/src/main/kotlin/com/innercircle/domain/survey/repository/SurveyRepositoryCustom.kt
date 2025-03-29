package com.innercircle.domain.survey.repository

fun interface WithDeletedFilterRepositoryCustom {
    fun enableDeletedFilter(showDeleted: Boolean)
}
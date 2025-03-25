package com.innercircle.domain.survey.repository

import jakarta.persistence.EntityManager
import org.hibernate.Session
import org.springframework.stereotype.Repository


@Repository
class SurveyWithFilterRepositoryImpl(
    private val entityManager: EntityManager
) : SurveyWithFilterRepositoryCustom {

    override fun enableDeletedFilter(showDeleted: Boolean) {
        val session = entityManager.unwrap(Session::class.java)
        val filter = session.enableFilter("deletedFilter")
        filter.setParameter("isDeleted", showDeleted)
    }
}
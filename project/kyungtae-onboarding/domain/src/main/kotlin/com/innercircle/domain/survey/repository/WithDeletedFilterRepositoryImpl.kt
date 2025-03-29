package com.innercircle.domain.survey.repository

import jakarta.persistence.EntityManager
import org.hibernate.Session
import org.springframework.stereotype.Repository


@Repository
class WithDeletedFilterRepositoryImpl(
    private val entityManager: EntityManager
) : WithDeletedFilterRepositoryCustom {

    override fun enableDeletedFilter(showDeleted: Boolean) {
        val session = entityManager.unwrap(Session::class.java)
        val filter = session.enableFilter("deletedFilter")
        filter.setParameter("isDeleted", showDeleted)
    }
}
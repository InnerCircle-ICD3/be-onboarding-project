package com.innercircle.survey.common

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DatabaseCleaner {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    private lateinit var tableNames: List<String>

    @Transactional
    fun truncate() {
        if (!::tableNames.isInitialized) {
            tableNames = entityManager.metamodel.entities
                .filter { it.javaType.getAnnotation(jakarta.persistence.Entity::class.java) != null }
                .map { it.name }
        }

        entityManager.flush()

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()

        tableNames.forEach { tableName ->
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}
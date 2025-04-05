package com.innercircle.domain.common

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.metamodel.EntityType
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
                .map { getPhysicalTableName(it) }
        }

        entityManager.flush()

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()

        tableNames.forEach { tableName ->
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }

    fun getPhysicalTableName(entity: EntityType<*>): String {
        val clazz = entity.javaType
        val tableAnnotation = clazz.getAnnotation(jakarta.persistence.Table::class.java)
        return if (tableAnnotation != null && tableAnnotation.name.isNotBlank()) {
            tableAnnotation.name
        } else {
            clazz.simpleName
                .replace(Regex("([a-z])([A-Z]+)"), "$1_$2")
                .lowercase()
        }
    }
}
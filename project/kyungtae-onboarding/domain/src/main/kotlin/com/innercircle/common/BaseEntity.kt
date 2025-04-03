package com.innercircle.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.FilterDef
import org.hibernate.annotations.ParamDef
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@FilterDef(
    name = "deletedFilter",
    parameters = [ParamDef(name = "isDeleted", type = Boolean::class)]
)
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {


    @CreatedDate
    var createdAt: LocalDateTime? = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    @Column(name = "is_deleted", nullable = false)
    var deleted = false

    var deletedAt: LocalDateTime? = null

    fun softDelete() {
        deleted = true
        deletedAt = LocalDateTime.now()
    }
}
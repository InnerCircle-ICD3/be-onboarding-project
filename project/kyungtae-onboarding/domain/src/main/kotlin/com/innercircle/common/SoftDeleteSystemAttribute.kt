package com.innercircle.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
class SoftDeleteSystemAttribute {

    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(1) DEFAULT 0")
    var deleted = false

    var deletedAt: LocalDateTime? = null

}
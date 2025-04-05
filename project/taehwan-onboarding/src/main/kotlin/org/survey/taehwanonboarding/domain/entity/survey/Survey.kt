package org.survey.taehwanonboarding.domain.entity.survey

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "survey")
class Survey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column
    var description: String? = null,

    @Version
    var version: Long = 0,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    var status: SurveyStatus = SurveyStatus.DRAFT,

    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableList<SurveyItem> = mutableListOf(),
) {
    fun addItem(item: SurveyItem) {
        items.add(item)
        item.survey = this
    }

    fun removeItem(item: SurveyItem) {
        items.remove(item)
        item.survey = null
    }

    enum class SurveyStatus {
        DRAFT,
        ACTIVE,
        CLOSED,
        ARCHIVED,
    }
}
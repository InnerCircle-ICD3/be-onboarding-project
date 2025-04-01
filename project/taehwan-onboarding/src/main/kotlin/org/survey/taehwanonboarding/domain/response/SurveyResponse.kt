package org.survey.taehwanonboarding.domain.response

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.survey.taehwanonboarding.domain.survey.Survey
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "survey_response")
class SurveyResponse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    val survey: Survey,

    // todo: 익명 응답 고려
    @Column
    var respondentId: String? = null,

    @Version
    var version: Long = 0,

    @CreatedDate
    @Column(updatable = false)
    var responseAt: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    var status: ResponseStatus = ResponseStatus.SUBMITTED,

    // todo: cascade 고려
    @OneToMany(mappedBy = "response")
    var items: MutableList<SurveyResponseItem> = mutableListOf(),
) {
    enum class ResponseStatus {
        PENDING,
        SUBMITTED,
    }

    fun addResponseItem(item: SurveyResponseItem) {
        items.add(item)
        item.response = this
    }
}
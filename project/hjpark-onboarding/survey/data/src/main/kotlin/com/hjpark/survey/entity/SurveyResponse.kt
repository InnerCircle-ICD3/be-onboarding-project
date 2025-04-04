package com.hjpark.survey.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "survey_response")
class SurveyResponse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    val survey: Survey,

    @Column(name = "respondent_id")
    val respondentId: String?,

    @Column(name = "status")
    val status: String?,

    @OneToMany(mappedBy = "surveyResponse", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<ResponseItem> = mutableListOf(),

    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    val createTime: LocalDateTime? = null
)

enum class ResponseStatus {
    IN_PROGRESS,
    COMPLETED
} 
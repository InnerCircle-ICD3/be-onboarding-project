package com.hjpark.survey.data.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "survey_response")
class SurveyResponse(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    var survey: Survey,

    @Column(name = "respondent_id")
    var respondentId: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: ResponseStatus = ResponseStatus.IN_PROGRESS,

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    val createTime: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "response", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<ResponseItem> = mutableListOf()
)

enum class ResponseStatus {
    IN_PROGRESS,
    COMPLETED
} 
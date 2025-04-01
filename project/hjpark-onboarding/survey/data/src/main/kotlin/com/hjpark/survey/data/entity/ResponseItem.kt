package com.hjpark.survey.data.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(
    name = "response_item",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uc_response_option",
            columnNames = ["response_id", "question_id", "option_id"]
        )
    ]
)
class ResponseItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    var response: SurveyResponse,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    var question: Question,

    @Column(name = "text_value")
    var textValue: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    var option: QuestionOption? = null,

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    val createTime: LocalDateTime = LocalDateTime.now()
) 
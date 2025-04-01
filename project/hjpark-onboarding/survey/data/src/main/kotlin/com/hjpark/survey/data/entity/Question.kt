package com.hjpark.survey.data.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "question")
class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    var survey: Survey,

    @Column(name = "sequence", nullable = false)
    var sequence: Short,

    @Column(name = "question_name", nullable = false)
    var name: String,

    @Column(name = "description")
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    var type: QuestionType,

    @Column(name = "required", nullable = false)
    var required: Boolean = false,

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    val createTime: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true)
    val options: MutableList<QuestionOption> = mutableListOf(),

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL])
    val responseItems: MutableList<ResponseItem> = mutableListOf()
)

enum class QuestionType {
    SHORT_ANSWER,
    LONG_ANSWER,
    SINGLE_CHOICE,
    MULTIPLE_CHOICE
} 
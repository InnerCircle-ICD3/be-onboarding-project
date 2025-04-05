package com.hjpark.survey.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "question")
class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    val survey: Survey,

    @Column(name = "sequence", nullable = false)
    var sequence: Short,

    @Column(name = "question_name", nullable = false)
    var name: String,

    @Column(name = "description")
    var description: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    var type: QuestionType,

    @Column(name = "required", nullable = false)
    var required: Boolean = false,

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("sequence ASC")
    val options: MutableList<QuestionOption> = mutableListOf(),

    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    val createTime: LocalDateTime? = null
)

enum class QuestionType {
    SHORT_ANSWER,    // 단답형
    LONG_ANSWER,     // 장문형
    SINGLE_CHOICE,   // 단일 선택 리스트
    MULTIPLE_CHOICE  // 다중 선택 리스트
}
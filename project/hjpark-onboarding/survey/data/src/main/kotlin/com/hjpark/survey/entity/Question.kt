package com.hjpark.survey.entity

import com.hjpark.survey.model.QuestionType
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
    val sequence: Short,

    @Column(name = "question_name", nullable = false)
    val name: String,

    @Column(name = "description")
    val description: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    val type: QuestionType,

    @Column(name = "required", nullable = false)
    val required: Boolean = false,

    @OneToMany(mappedBy = "question", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("sequence ASC")
    val options: MutableList<QuestionOption> = mutableListOf(),

    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    val createTime: LocalDateTime? = null
)
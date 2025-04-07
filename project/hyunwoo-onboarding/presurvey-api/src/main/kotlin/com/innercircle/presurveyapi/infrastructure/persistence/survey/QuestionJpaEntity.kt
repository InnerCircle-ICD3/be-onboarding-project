package com.innercircle.presurveyapi.infrastructure.persistence.survey

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "question")
class QuestionJpaEntity(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    var survey: SurveyJpaEntity? = null,

    val title: String,

    val description: String,

    val type: String, // Enum을 문자열로 저장 (QuestionType.name)

    val required: Boolean,

    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_id")
    val options: List<QuestionOptionJpaEntity> = emptyList()
) {

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    lateinit var createDate: LocalDateTime
}
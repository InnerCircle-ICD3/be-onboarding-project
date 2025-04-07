package com.innercircle.presurveyapi.infrastructure.persistence.survey

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "question_option")
class QuestionOptionJpaEntity(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    val text: String
) {

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    lateinit var createDate: LocalDateTime
}
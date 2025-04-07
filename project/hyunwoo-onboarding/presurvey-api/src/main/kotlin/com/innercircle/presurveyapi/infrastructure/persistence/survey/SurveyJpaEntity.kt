package com.innercircle.presurveyapi.infrastructure.persistence.survey

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "survey")
class SurveyJpaEntity(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    val title: String,

    val description: String,

    @OneToMany(
        mappedBy = "survey",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    var questions: MutableList<QuestionJpaEntity> = mutableListOf(),

) {

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    lateinit var createDate: LocalDateTime
}
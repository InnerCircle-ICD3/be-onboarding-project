package com.hjpark.survey.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "survey")
class Survey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    val id: Long? = null,

    @Column(name = "survey_name", nullable = false)
    var name: String,

    @Column(name = "description")
    var description: String?,

    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("sequence ASC")
    val questions: MutableList<Question> = mutableListOf(),

    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    val createTime: LocalDateTime? = null,

    @Column(name = "update_time", nullable = false)
    @UpdateTimestamp
    val updateTime: LocalDateTime? = null
) 
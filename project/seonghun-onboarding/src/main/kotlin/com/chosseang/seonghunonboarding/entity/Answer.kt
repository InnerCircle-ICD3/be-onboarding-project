package com.chosseang.seonghunonboarding.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    val name: String,

    @Column(columnDefinition = "TEXT")
    val items: String,

    @Column(columnDefinition = "TEXT")
    val responses: String,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey
)

package com.example.entity

import jakarta.persistence.*

@Entity
class Survey(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,

    val description: String? = null,

    @OneToMany(mappedBy = "survey", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<SurveyItem> = mutableListOf()
)

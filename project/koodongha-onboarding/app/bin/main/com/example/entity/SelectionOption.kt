package com.example.entity

import jakarta.persistence.*

@Entity
class SelectionOption(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val value: String,

    @ManyToOne
    @JoinColumn(name = "survey_item_id")
    val surveyItem: SurveyItem
)

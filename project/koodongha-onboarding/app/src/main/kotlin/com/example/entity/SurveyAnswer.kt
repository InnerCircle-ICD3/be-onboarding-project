package com.example.entity

import jakarta.persistence.*

@Entity
class SurveyAnswer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val shortAnswer: String? = null,
    
    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    @ManyToOne
    @JoinColumn(name = "survey_item_id")
    val surveyItem: SurveyItem,

    @ManyToMany
    @JoinTable(
        name = "survey_answer_selection_option",
        joinColumns = [JoinColumn(name = "answer_id")],
        inverseJoinColumns = [JoinColumn(name = "option_id")]
    )
    val selectedOptions: MutableList<SelectionOption> = mutableListOf()
)

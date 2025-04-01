package com.example.entity

import jakarta.persistence.*

@Entity
class SurveyItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    val description: String? = null,

    @Enumerated(EnumType.STRING)
    val inputType: InputType,

    val isRequired: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,

    @OneToMany(mappedBy = "surveyItem", cascade = [CascadeType.ALL], orphanRemoval = true)
    val options: MutableList<SelectionOption> = mutableListOf()
)

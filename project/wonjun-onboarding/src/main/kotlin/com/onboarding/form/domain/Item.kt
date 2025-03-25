package com.onboarding.form.domain

import jakarta.persistence.*

@Entity
class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "survey_id")
    val survey: Survey,
    val title: String,
    val description: String,
    @Enumerated(EnumType.STRING)
    val type: ItemType,
    val isRequired: Boolean,
    @ElementCollection
    @CollectionTable
    val options: MutableList<String> = mutableListOf()
)
